import { In, Repository, SelectQueryBuilder } from 'typeorm';
import { Series } from '../../entity/Series';
import { makeQueryBuilderOrWhere, throwError } from './utils';
import { ActionSeparatedSeason, CategoryDto, SeriesUpdateDto, UploadableImage } from '../types';
import { iSeriesService } from '../SeriesService';
import { Season } from '../../entity/Season';
import { mysqlDataSource } from '../../data-source';
import { Category } from '../../entity/Category';
import { Image } from '../../entity/Image';
import { ImageService } from './ImageService';
import { iImageService } from '../ImageService';

export class SeriesService implements iSeriesService {
  private repository: Repository<Series>;
  private seasonRepository: Repository<Season>;
  private imageService: iImageService;
  constructor(repository?: Repository<Series>, seasonRepository?: Repository<Season>, imageService?: iImageService) {
    this.repository = repository ? repository : mysqlDataSource.getRepository(Series);
    this.seasonRepository = seasonRepository ? seasonRepository : mysqlDataSource.getRepository(Season);
    this.imageService = imageService ? imageService : new ImageService();
  }

  findByPageAndSizeAndFilterAndOrder = async (page: number, size: number, filter?: string, order?: string, ascendingDirection: boolean = false): Promise<[Series[], number]> => {
    const direction = ascendingDirection ? "ASC" : "DESC";
    const query: SelectQueryBuilder<Series> = this.repository.createQueryBuilder('series')
    .leftJoinAndSelect('series.categories', 'category')
    .leftJoinAndSelect('series.seasons', 'season')
    .leftJoinAndSelect('series.image', 'image')
    .skip((page - 1) * size)
    .take(size);

    if(filter) {
      const fields: string[] = ['series.title', 'series.prod_year', 'category.name'];
      makeQueryBuilderOrWhere(query, filter, fields);
    }
    
    if(order) {
      switch (order) {
        case "prod_year":
          query.orderBy('series.prod_year', direction)
            .addOrderBy('series.added', direction);
          break;
        case "age_limit":
          query.orderBy('series.age_limit', direction);
          break;
        case "length":
          query.orderBy('series.length', direction);
          break;
        case "title":
        default:
          query.orderBy('series.title', direction);
          break;
      }
    } else {
      query.orderBy('series.id', direction);
    }

    return await query.getManyAndCount();
  };

  findOne = async (id: number): Promise<Series> => {
    
    const series = await this.repository.findOne({relations: {
        seasons: true,
        categories: true,
        image: true,
      },
      where: {
        id
      }
    })
    if(!series) {
      throwError('404', 'Couldn\'t find series!');
    }

    return series;
  };

  save = async (entity: Series): Promise<Series> => {
    const createdSeries = this.repository.create(entity);
    if(!createdSeries) {
      throwError('400', 'No series is given to save!');
    }
    createdSeries.id = undefined;

    if(entity.image) {
      const newName = this.imageService.renameFromTempToMand(entity.image as unknown as UploadableImage);
      const image: Image = { name: newName } as Image;
      if(entity.image.x_offset) {
        image.x_offset = entity.image.x_offset;
      }
      if(entity.image.y_offset) {
        image.y_offset = entity.image.y_offset;
      }
      createdSeries.image = image;
    }

    const savedSeries = await this.repository.save(createdSeries);
    if(!savedSeries) {
      throwError('400', 'Couldn\'t save series!');
    }

    return savedSeries;
  };

  update = async (id: number, entity: SeriesUpdateDto): Promise<boolean> => {
    const createdSeries = this.repository.create(entity);
    if(!createdSeries) {
      throwError('400', 'No series is given to update!');
    }
    createdSeries.id = Number(id);

    const dbSeries = await this.repository.findOne({
      where: { id: id },
      relations: ['seasons', 'categories']
    });
    if(!dbSeries) {
      throwError('400', `There is no series with id ${id}!`);
    }

    // Kép műveletek
    if(entity.image && entity.image.id) {
      createdSeries.image = entity.image;
    } else if(entity.image) {
      const newName = this.imageService.renameFromTempToMand(entity.image as unknown as UploadableImage);
      const image: Image = { name: newName } as Image;

      if(!dbSeries.image) {
        if(entity.image.x_offset) {
          image.x_offset = entity.image.x_offset;
        }
        if(entity.image.y_offset) {
          image.y_offset = entity.image.y_offset;
        }
        createdSeries.image = image;

      } else if(entity.image.name !== dbSeries.image.name) {  
        this.imageService.removeImageFromDir(dbSeries.image.name);

        if(entity.image.x_offset) {
          image.x_offset = entity.image.x_offset;
        }
        if(entity.image.y_offset) {
          image.y_offset = entity.image.y_offset;
        }
        createdSeries.image = image;
      }
    }

    //Évad műveletek
    if(createdSeries.seasons) {
      createdSeries.seasons = await this.makeSeasons(entity, createdSeries.id, createdSeries.seasons);
    }

    if(!createdSeries.seasons || createdSeries.seasons.length < 1) {
      delete createdSeries.seasons;
    }

    //Kategória műveletek
    if(createdSeries.categories && createdSeries.categories.length > 0) {
      const removableCategoriesMap = this.getRemovableCategories(entity.categories);
      const categoriesMap = this.makeMapFromArray<Category>(dbSeries.categories);
  
      for(let category of createdSeries.categories) {
        categoriesMap.set(category.id, category);
      }
      createdSeries.categories = [];
      categoriesMap.forEach((value, id) => {
        if(!removableCategoriesMap.get(id)){
          createdSeries.categories.push({ id } as Category);
        }
      });
    }

    
    // Frissítés
    const updatedSeries = await this.repository.save(createdSeries);
    if(entity.image && !entity.image.id && dbSeries.image) {
      await this.imageService.removeImageFromDb(dbSeries.image.name);
    }
    if(!updatedSeries) {
      throwError('400', 'Couldn\'t update series!');
    }

    return true;
  };

  deleteImage = async (id: number): Promise<boolean> => {
    try {
      const series = await this.repository.findOneBy({ id });
      const image = series.image;
      series.image = null;

      const seriesResponse = await this.repository.save(series);
      if(!seriesResponse) {
        throwError("400", "Can't remove image!");
      }
      
      this.imageService.removeImageFromDir(image.name);
      await this.imageService.removeImageFromDb(image.name);
      return true;
    } catch(err) {
      throwError("400", "Something went wrong during image deletion!");
    }
  }

  private deleteSeasons = async (deleteSeasons: number[]) => {
    if(deleteSeasons.length > 0) {
      const result = await this.seasonRepository.delete(deleteSeasons);
      if(!result || result.affected < 1) {
        throwError('400', 'Some problem occured during deleting seasons!');
      }
    }
  }

  private makeActionSeparatedSeasons = async (seasons: Season[]): Promise<ActionSeparatedSeason> => {
    const actionSeasons: ActionSeparatedSeason = {deleteSeasons: [], saveOrUpdateSeasons: []};
    if(!seasons || seasons.length < 1) {
      return actionSeasons;
    }

    for(const season of seasons) {
      if(!season.season && !season.episode && season.id) {
        actionSeasons.deleteSeasons.push(season.id);
        continue;
      }

      if(((season.season || season.episode) && season.id) || (season.season && season.episode && !season.id)) {
        actionSeasons.saveOrUpdateSeasons.push(season);
        continue;
      }

      throwError('400', 'Some problem occured during calculation of season updates!');
    }

    return actionSeasons
  }

  private makeMapFromArray = <TYPE>(list: any[]): Map<number, TYPE> => {
    const map: Map<number, TYPE> = new Map<number, TYPE>();
    if(!list || list.length < 1) {
      return map;
    }

    for(let item of list) {
      map.set(item.id, item);
    }

    return map;
  }

  private getRemovableCategories = (categories: CategoryDto[]): Map<number, boolean> => {
    const removableCategoriesMap: Map<number, boolean> = new Map<number, boolean>();
    if(!categories || categories.length < 1) {
      return removableCategoriesMap
    }

    categories.forEach(category => {
      if(category.remove){
        removableCategoriesMap.set(category.id, true);
      }
    });

    return removableCategoriesMap;
  }

  private makeSeasons = async (entity: SeriesUpdateDto, id: number, seasons: Season[]): Promise<Season[] | undefined> => {
    //Season ID-t kell ellenőrizni, hogy az tényleg a mi sorozatunkhoz tartozik-e!
    const seasonIds = entity.seasons.map(s => s?.id).filter(s => s);
    const dbSeasons = await this.seasonRepository.findBy({
      id: In(seasonIds),
      series: {
        id: id
      }
    });

    if(dbSeasons.length !== seasonIds.length) {
      throwError('400', 'Can\'t update series, because of the given seasons!');
    }

    const actionSeasons: ActionSeparatedSeason = await this.makeActionSeparatedSeasons(seasons);
    
    await this.deleteSeasons(actionSeasons.deleteSeasons);
    if(actionSeasons.saveOrUpdateSeasons.length > 0) {
      return actionSeasons.saveOrUpdateSeasons;
    }

    return undefined;
  }
}