import { FindManyOptions, Repository } from 'typeorm';
import { Series } from '../../entity/Series';
import mysqlDataSource from '../../data-source';
import { makeWhereOptions, throwError } from './utils';
import { FilterFields } from '../types';
import { iSeriesService } from '../SeriesService';

export class SeriesService implements iSeriesService {
  private repository: Repository<Series>;
  constructor(repository?: Repository<Series>) {
    this.repository = repository ? repository : mysqlDataSource.getRepository(Series);
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
    createdSeries.seasons = undefined;

    const dbSeries = await this.repository.findOneBy({id: id});
    if(!dbSeries) {
      throwError('400', `There is no series with id ${id}!`);
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