import { In, Repository, SelectQueryBuilder } from 'typeorm';
import { Series } from '../../entity/Series';
import { makeQueryBuilderOrWhere, throwError } from './utils';
import { ActionSeparatedSeason, CategoryDto, SeriesUpdateDto } from '../types';
import { iSeriesService } from '../SeriesService';
import { Season } from '../../entity/Season';
import { dataSource } from '../../plugins/autoload/dataSource';
import { Category } from '../../entity/Category';

export class SeriesService implements iSeriesService {
  private repository: Repository<Series>;
  private seasonRepository: Repository<Season>;
  constructor(repository?: Repository<Series>, seasonRepository?: Repository<Season>) {
    this.repository = repository ? repository : dataSource.getRepository(Series);
    this.seasonRepository = seasonRepository ? seasonRepository : dataSource.getRepository(Season);
  }

  findByPageAndSizeAndFilterAndOrder = async (page: number, size: number, filter?: string, order?: string, ascendingDirection: boolean = false): Promise<[Series[], number]> => {
    const direction = ascendingDirection ? "ASC" : "DESC";
    const query: SelectQueryBuilder<Series> = this.repository.createQueryBuilder('series')
    .leftJoinAndSelect('series.categories', 'category')
    .leftJoinAndSelect('series.seasons', 'season')
    .skip((page - 1) * size)
    .take(size);

    if(filter) {
      const fields: string[] = ['series.title', 'series.prodYear', 'category.name'];
      makeQueryBuilderOrWhere(query, filter, fields);
    }
    
    if(order) {
      switch (order) {
        case "prodYear":
          query.orderBy('series.prodYear', direction)
            .addOrderBy('series.added', direction);
          break;
        case "ageLimit":
          query.orderBy('series.ageLimit', direction);
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
    if(!updatedSeries) {
      throwError('400', 'Couldn\'t update series!');
    }

    return true;
  };

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
      //season.series = createdSeries;
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