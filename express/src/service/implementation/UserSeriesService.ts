import { Repository, SelectQueryBuilder } from 'typeorm';
import { UserSeries } from '../../entity/UserSeries';
import { makeStringOrWhere, throwError } from './utils';
import { iUserSeriesService } from '../UserSeriesService';
import { mysqlDataSource } from '../../data-source';
import { User } from '../../entity/User';

export class UserSeriesService implements iUserSeriesService {
  private repository: Repository<UserSeries>;
  constructor(repository?: Repository<UserSeries>) {
    this.repository = repository ? repository : mysqlDataSource.getRepository(UserSeries);
  }

  findByPageAndSizeAndFilterAndStatusAndOrder = async (userId: number, page: number, size: number, filter?: string, status?: number, order?: string, ascendingDirection: boolean = false): Promise<[UserSeries[], number]> => {
    const direction = ascendingDirection ? "ASC" : "DESC";
    const query: SelectQueryBuilder<UserSeries> = this.repository.createQueryBuilder('userseries')
    .leftJoinAndSelect('userseries.series', 'series')
    .leftJoinAndSelect('series.categories', 'category')
    .leftJoinAndSelect('series.seasons', 'season')
    .leftJoinAndSelect('userseries.status', 'status')
    .skip((page - 1) * size)
    .take(size);

    let baseWhereStatement: string = `userseries.userId = ${userId}`;

    if(status) {
      baseWhereStatement += ` AND status.id = ${status}`;
    }

    if(filter) {
      const fields: string[] = ['series.title', 'series.prodYear', 'category.name'];
      const whereStatement = makeStringOrWhere(filter, fields);
      query.where(`${baseWhereStatement} AND ${whereStatement}`);
    } else {
      query.where(baseWhereStatement);
    }

    if(order) {
      switch (order) {
        case 'title':
          query.orderBy('series.title', direction);
          break;
        case 'prodYear':
          query.orderBy('series.prodYear', direction);
          break;
        case 'ageLimit':
          query.orderBy('series.ageLimit', direction);
          break;
        case 'length':
          query.orderBy('series.length', direction);
          break;
        case 'modification':
        default:
          query.orderBy('userseries.modification', direction);
          break;
      }
    } else {
      query.orderBy('series.id', direction);
    }

    
    return query.getManyAndCount();
  };

  findOne = async (userId: number, seriesId: number): Promise<UserSeries> => {
    const userSeries = await this.repository.findOne({
      where: {
        user: { id: userId },
        series: { id: seriesId },
      },
      relations: ['series', 'series.seasons', 'series.categories', 'status']
    });
    if(!userSeries) {
      throwError('404', `There is no userSeries, with userId ${userId} and seriesId ${seriesId}`);
    }

    return userSeries;
  };

  save = async (userId: number, entity: UserSeries): Promise<UserSeries> => {
    const createdUserSeries = this.repository.create(entity);
    if(!createdUserSeries) {
      throwError('400', 'No userseries is given to save!');
    }
    
    if(!userId || !createdUserSeries.series) {
      throwError('400', 'No user or series is given to save!');
    }
    createdUserSeries.user = { id: userId} as User;
    
    const dbUserSeries = await this.repository.findOne({
      where: {
        user: { id: createdUserSeries.user?.id },
        series: { id: createdUserSeries.series?.id },
      },
      relations: ['series', 'series.seasons', 'series.categories', 'status']
    });
    if(dbUserSeries) {
      throwError('400', 'Userseries with the given userId and seriesId already exists!');
    }

    const savedUserSeries = await this.repository.save(createdUserSeries);
    if(!savedUserSeries) {
      throwError('400', 'Couldn\'t save userseries!');
    }

    return savedUserSeries;
  };

  update = async (userId: number, seriesId: number, entity: UserSeries): Promise<boolean> => {
    const createdUserSeries = this.repository.create(entity);
    if(!createdUserSeries) {
      throwError('400', 'No userseries is given to update!');
    }
    createdUserSeries.user = undefined;
    createdUserSeries.series = undefined;

    const dbUserSeries = await this.repository.findOne({
      where: {
        user: { id: userId },
        series: { id: seriesId },
      },
      relations: ['series', 'series.seasons', 'series.categories', 'status']
    });
    if(!dbUserSeries) {
      throwError('404', `User with id ${userId}, doesn't have series with id ${seriesId}!`);
    }
    
    const updatedUserSeries = await this.repository.update({
      user: { id: userId },
      series: { id: seriesId }
    }, createdUserSeries);
    if(!updatedUserSeries || updatedUserSeries.affected < 1) {
      throwError('400', 'Couldn\'t update userseries!');
    }

    return true;
  };

  remove = async (userId: number, seriesId: number): Promise<number> => {
    const removedNewsFeed = await this.repository.delete({
      user: { id: userId },
      series: { id: seriesId }
    })
    if(!removedNewsFeed || removedNewsFeed.affected < 1) {
      throwError('400', 'Couldn\'t remove userseries!');
    }
    
    return seriesId;
  };
}