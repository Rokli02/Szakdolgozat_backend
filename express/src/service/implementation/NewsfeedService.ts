import { FindManyOptions, Repository } from 'typeorm';
import { NewsFeed } from '../../entity/NewsFeed';
import mysqlDataSource from '../../data-source';
import { makeWhereOptions, throwError } from './utils';
import { FilterFields } from '../types';
import { iNewsFeedService } from '../NewsFeedService';

export class NewsFeedService implements iNewsFeedService {
  private repository: Repository<NewsFeed>;
  constructor(repository?: Repository<NewsFeed>) {
    this.repository = repository ? repository : mysqlDataSource.getRepository(NewsFeed);
  }

  findByPageAndSizeAndFilterAndOrder = async (page: number, size: number, filter?: string, order?: string, ascendingDirection: boolean = false): Promise<[NewsFeed[], number]> => {
    const options: FindManyOptions<NewsFeed> = {
      skip: ((page - 1) * size),
      take: size
    };

    if(filter) {
      const fields: FilterFields[] = [{name: 'series', field: 'title'}, 'title', 'modification'];
      options.where = makeWhereOptions(filter, fields);
    }

    if(order) {
      switch (order) {
        case "series":
          options.order = {
            series: {
              title: ascendingDirection ? 'ASC' : 'DESC', 
            }
          }
          break;
        case "title":
          options.order = {
            title: ascendingDirection ? 'ASC' : 'DESC', 
          }
          break;
        case "modification":
        default:
          options.order = {
            modification: ascendingDirection ? 'ASC' : 'DESC', 
          }
          break;
      }
    } else {
      options.order = {
        id: ascendingDirection ? 'ASC' : 'DESC', 
      }
    }

    const newsfeeds = await this.repository.findAndCount(options);
    return newsfeeds;
  };

  findByUserAndPageAndSizeAndFilterAndOrder = async (userId: number, page: number, size: number, filter?: string, order?: string, ascendingDirection: boolean = false): Promise<[NewsFeed[], number]> => {
    const options: FindManyOptions<NewsFeed> = {
      skip: ((page - 1) * size),
      take: size
    };

    //TODO:
    //Olyanra kialakítani, hogy a newsfeednél kiírassa azokat, amelyik seriesId-ja benne van a userSeries-ben a userId mellett!
    if(filter) {
      const fields: FilterFields[] = [{name: 'series', field: 'title'}, 'title', 'modification'];
      options.where = makeWhereOptions(filter, fields);
    }

    if(order) {
      switch (order) {
        case "series":
          options.order = {
            series: {
              title: ascendingDirection ? 'ASC' : 'DESC', 
            }
          }
          break;
        case "title":
          options.order = {
            title: ascendingDirection ? 'ASC' : 'DESC', 
          }
          break;
        case "modification":
        default:
          options.order = {
            modification: ascendingDirection ? 'ASC' : 'DESC', 
          }
          break;
      }
    } else {
      options.order = {
        id: ascendingDirection ? 'ASC' : 'DESC', 
      }
    }

    throw new Error('Not implemented!');
    const newsfeeds = await this.repository.findAndCount(options);
    return newsfeeds;
  };

  findOne = async (id: number): Promise<NewsFeed> => {
    const newsFeed = await this.repository.findOneBy({id: id});
    if(!newsFeed) {
      throwError('404', `There is no newsfeed with id ${id}!`);
    }

    return newsFeed;
  };

  save = async (entity: NewsFeed): Promise<NewsFeed> => {
    const createdNewsFeed = this.repository.create(entity);
    if(!createdNewsFeed) {
      throwError('400', 'No newsfeed is given to save!');
    }
    createdNewsFeed.id = undefined;

    const savedNewsFeed = await this.repository.save(createdNewsFeed);
    if(!savedNewsFeed) {
      throwError('400', 'Couldn\'t save newsfeed!');
    }

    return savedNewsFeed;
  };

  update = async (id: number, entity: NewsFeed): Promise<boolean> => {
    const createdNewsFeed = this.repository.create(entity);
    if(!createdNewsFeed) {
      throwError('400', 'No category is given to save!');
    }
    createdNewsFeed.id = id;

    const dbNewsFeed = await this.repository.findOneBy({id: id});
    if(!dbNewsFeed) {
      throwError('404', `There is no category with id ${id}!`);
    }

    const updatedNewsFeed = await this.repository.update({id: id}, createdNewsFeed);
    console.log("updatedNewsFeed: ");
    console.log(updatedNewsFeed);
    if(!updatedNewsFeed || updatedNewsFeed.affected < 1) {
      throwError('400', 'Couldn\'t update category!');
    }

    return true;
  };

  remove = async (id: number): Promise<number> => {
    const removedNewsFeed = await this.repository.delete({id: id});
    if(!removedNewsFeed || removedNewsFeed.affected < 1) {
      throwError('400', 'Couldn\'t remove newsfeed!');
    }

    return id;
  };
}