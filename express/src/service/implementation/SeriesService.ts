import { FindManyOptions, In, Repository } from 'typeorm';
import { Series } from '../../entity/Series';
import mysqlDataSource from '../../data-source';
import { makeWhereOptions, throwError } from './utils';
import { ActionSeparatedSeason, FilterFields } from '../types';
import { iSeriesService } from '../SeriesService';
import { Season } from '../../entity/Season';

export class SeriesService implements iSeriesService {
  private repository: Repository<Series>;
  private seasonRepository: Repository<Season>;
  constructor(repository?: Repository<Series>, seasonRepository?: Repository<Season>) {
    this.repository = repository ? repository : mysqlDataSource.getRepository(Series);
    this.seasonRepository = seasonRepository ? seasonRepository : mysqlDataSource.getRepository(Season);
  }

  findByPageAndSizeAndFilterAndOrder = async (page: number, size: number, filter?: string, order?: string, ascendingDirection: boolean = false): Promise<[Series[], number]> => {
    const options: FindManyOptions<Series> = {
      skip: ((page - 1) * size),
      take: size
    };

    if(filter) {
      const fields: FilterFields[] = ['title', 'prodYear', {name: 'categories', field: 'name'}];
      options.where = makeWhereOptions(filter, fields);
    }

    if(order) {
      switch (order) {
        case "prodYear":
          options.order = {
            prodYear: ascendingDirection ? "ASC" : "DESC",
            added: ascendingDirection ? "ASC" : "DESC",
          }
          break;
        case "ageLimit":
          options.order = {
            ageLimit: ascendingDirection ? "ASC" : "DESC",
          }
          break;
        case "length":
          options.order = {
            length: ascendingDirection ? "ASC" : "DESC",
          }
          break;
        case "title":
        default:
          options.order = {
            title: ascendingDirection ? "ASC" : "DESC",
          }
          break;
      }
    } else {
      options.order = {
        id: ascendingDirection ? "ASC" : "DESC",
      }
    }
    
    const serieses = await this.repository.findAndCount(options);
    return serieses;
  };

  findOne = async (id: number): Promise<Series> => {
    const series = await this.repository.findOneBy({id: id});
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

  update = async (id: number, entity: Series): Promise<boolean> => {
    const createdSeries = this.repository.create(entity);
    if(!createdSeries) {
      throwError('400', 'No series is given to update!');
    }
    createdSeries.id = id;

    const dbSeries = await this.repository.findOneBy({id: id});
    if(!dbSeries) {
      throwError('400', `There is no series with id ${id}!`);
    }

    //Season ID-t kell ellenőrizni, hogy az tényleg a mi sorozatunkhoz tartozik-e!
    const seasonIds = entity.seasons.map(s => s?.id).filter(s => s);
    const dbSeasons = await this.seasonRepository.findBy({
      id: In(seasonIds),
      series: {
        id: id
      }
    });

    if(dbSeasons.length !== seasonIds.length) {
      throwError('400', 'Can\'t update series, because of the gives seasons!');
    }

    const actionSeasons: ActionSeparatedSeason = {deleteSeasons: [], saveSeasons: [], updateSeasons: []};
    for(const season of createdSeries.seasons) {
      season.series = createdSeries;
      if(!season.season && !season.episode && season.id) {
        actionSeasons.deleteSeasons.push(season.id);
        continue;
      }

      if((season.season || season.episode) && season.id) {
        actionSeasons.updateSeasons.push(season);
        continue;
      }

      if(season.season && season.episode && !season.id) {
        actionSeasons.saveSeasons.push(season);
        continue;
      }

      throwError('400', 'Some problem occured during calculation of season updates!');
    }
    createdSeries.seasons = undefined;

    //season műveletek
    if(actionSeasons.deleteSeasons.length > 0) {
      const result = await this.seasonRepository.delete(actionSeasons.deleteSeasons);
      if(!result || result.affected < 1) {
        throwError('400', 'Some problem occured during deleting seasons!');
      }
    }
    if(actionSeasons.updateSeasons.length > 0) {
      //Talán le kell kérdezni az adatbázisba levő adatokat
      const result = await this.seasonRepository.save(actionSeasons.updateSeasons);
      if(!result || result.length < 1) {
        throwError('400', 'Some problem occured during updating seasons!');
      }
    }
    if(actionSeasons.saveSeasons.length > 0) {
      const result = await this.seasonRepository.save(actionSeasons.saveSeasons);
      if(!result || result.length < 1) {
        throwError('400', 'Some problem occured during saving seasons!');
      }
    }

    const updatedSeries = await this.repository.update({id: id}, createdSeries);
    console.log("updatedSeries: ");
    console.log(updatedSeries);
    if(!updatedSeries || updatedSeries.affected < 1) {
      throwError('400', 'Couldn\'t update series!');
    }

    return true;
  };
}