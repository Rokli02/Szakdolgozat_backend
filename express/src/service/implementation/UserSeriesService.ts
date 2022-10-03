import { FindManyOptions, Repository } from 'typeorm';
import { UserSeries } from '../../entity/UserSeries';
import mysqlDataSource from '../../data-source';
import { makeWhereOptionsForUserSeries, throwError } from './utils';
import { FilterFields } from '../types';
import { iUserSeriesService } from '../UserSeriesService';

export class UserSeriesService implements iUserSeriesService {
  private repository: Repository<UserSeries>;
  constructor(repository?: Repository<UserSeries>) {
    this.repository = repository ? repository : mysqlDataSource.getRepository(UserSeries);
  }

  findByPageAndSizeAndFilterAndStatusAndOrder = async (userId: number, page: number, size: number, filter?: string, status?: number, order?: string, ascendingDirection: boolean = false): Promise<[UserSeries[], number]> => {
    const options: FindManyOptions<UserSeries> = {
      skip: ((page - 1) * size),
      take: size
    };

    if(filter) {
      const fields: FilterFields[] = ['title', 'prodYear', { name: 'categories', field: 'name' }];
      options.where = makeWhereOptionsForUserSeries(userId, filter, fields, status);
    }

    if(order) {
      switch (order) {
        case 'title':
          options.order = {
            series: {
              title: ascendingDirection ? 'ASC' : 'DESC',
            }
          }
          break;
        case 'prodYear':
          options.order = {
            series: {
              prodYear: ascendingDirection ? 'ASC' : 'DESC',
            }
          }
          break;
        case 'ageLimit':
          options.order = {
            series: {
              ageLimit: ascendingDirection ? 'ASC' : 'DESC',
            }
          }
          break;
        case 'length':
          options.order = {
            series: {
              length: ascendingDirection ? 'ASC' : 'DESC',
            }
          }
          break;
        case 'modification':
        default:
          options.order = {
            modification: ascendingDirection ? 'ASC' : 'DESC',
          }
          break;
      }
    } else {
      options.order = {
        series: {
          id: ascendingDirection ? "ASC" : "DESC",
        }
      }
    }

    const userSeries = await this.repository.findAndCount(options);
    return userSeries;
  };

  findOne = async (userId: number, seriesId: number): Promise<UserSeries> => {
    const userSeries = await this.repository.findOneBy({
      user: { id: userId },
      series: { id: seriesId },
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
    
    console.log(createdUserSeries);
    if(!userId || !createdUserSeries.series) {
      throwError('400', 'No user or series is given to save!');
    }
    createdUserSeries.user.id = userId;
    
    const dbUserSeries = await this.repository.findOneBy({
      user: { id: createdUserSeries.user?.id },
      series: { id: createdUserSeries.series?.id },
    })
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

    const dbUserSeries = await this.repository.findOneBy({
      user: { id: userId },
      series: { id: seriesId }
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