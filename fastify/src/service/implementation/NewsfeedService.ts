import { Repository, SelectQueryBuilder } from 'typeorm';
import { NewsFeed } from '../../entity/NewsFeed';
//import mysqlDataSource from '../../data-source';
import { makeQueryBuilderOrWhere, makeStringOrWhere, throwError } from './utils';
import { iNewsFeedService } from '../NewsFeedService';

export class NewsFeedService implements iNewsFeedService {
  private repository: Repository<NewsFeed>;
  constructor(repository?: Repository<NewsFeed>) {
    this.repository = repository// ? repository : mysqlDataSource.getRepository(NewsFeed);
  }

  findByPageAndSizeAndFilterAndOrder = async (page: number, size: number, filter?: string, order?: string, ascendingDirection: boolean = false): Promise<[NewsFeed[], number]> => {
    const direction = ascendingDirection ? "ASC" : "DESC";
    const query: SelectQueryBuilder<NewsFeed> = this.repository.createQueryBuilder('newsfeed')
    .leftJoinAndSelect('newsfeed.series', 'series')
    .skip((page - 1) * size)
    .take(size);

    if(filter) {
      const fields: string[] = ['series.title', 'newsfeed.title', 'newsfeed.modification'];
      makeQueryBuilderOrWhere(query, filter, fields);
    }

    if(order) {
      switch (order) {
        case "series":
          query.orderBy('series.title', direction);
          break;
        case "title":
          query.orderBy('newsfeed.title', direction);
          break;
        case "modification":
        default:
          query.orderBy('newsfeed.modification', direction);
          break;
      }
    } else {
      query.orderBy('newsfeed.id', direction);
    }

    return query.getManyAndCount();
  };
  
  findByUserAndPageAndSizeAndFilterAndOrder = async (userId: number, page: number, size: number, filter?: string, order?: string, ascendingDirection: boolean = false): Promise<[NewsFeed[], number]> => {
    const direction = ascendingDirection ? "ASC" : "DESC";
    const query: SelectQueryBuilder<NewsFeed> = this.repository.createQueryBuilder('newsfeed')
    .leftJoinAndSelect('newsfeed.series', 'series')
    .leftJoin('series.userserieses', 'userseries')
    .skip((page - 1) * size)
    .take(size);

    if(filter) {
      const fields: string[] = ['series.title', 'newsfeed.title', 'newsfeed.modification'];
      const whereStatement = makeStringOrWhere(filter, fields);
      query.where(`userseries.userId = ${userId} AND ${whereStatement}`)
    } else {
      query.where('userseries.userId = :userId', {userId});
    }

    if(order) {
      switch (order) {
        case "series":
          query.orderBy('series.title', direction);
          break;
        case "title":
          query.orderBy('newsfeed.title', direction);
          break;
        case "modification":
        default:
          query.orderBy('newsfeed.modification', direction);
          break;
      }
    } else {
      query.orderBy('newsfeed.id', direction);
    }

    return query.getManyAndCount();
  };

  findOne = async (id: number): Promise<NewsFeed> => {
    const newsFeed = await this.repository.findOne({
      where: {id},
      relations: {series: true},
    });
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

    const dbNewsFeed = await this.repository.findOneBy({id});
    if(!dbNewsFeed) {
      throwError('404', `There is no category with id ${id}!`);
    }

    const updatedNewsFeed = await this.repository.update({id}, createdNewsFeed);
    console.log("updatedNewsFeed: ");
    console.log(updatedNewsFeed);
    if(!updatedNewsFeed || updatedNewsFeed.affected < 1) {
      throwError('400', 'Couldn\'t update category!');
    }

    return true;
  };

  remove = async (id: number): Promise<number> => {
    const removedNewsFeed = await this.repository.delete({id});
    if(!removedNewsFeed || removedNewsFeed.affected < 1) {
      throwError('400', 'Couldn\'t remove newsfeed!');
    }

    return id;
  };
}