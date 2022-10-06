import { FindManyOptions, Repository } from 'typeorm';
import { User } from '../../entity/User';
//import mysqlDataSource from '../../data-source';
import { makeWhereOptions, throwError } from './utils';
import { FilterFields } from '../types';
import { genSalt, hash } from 'bcrypt';
import { iUserService } from '../UserService';

export class UserService implements iUserService {
  private repository: Repository<User>;
  constructor(repository?: Repository<User>) {
    this.repository = repository// ? repository : mysqlDataSource.getRepository(User);
  }

  findByPageAndSizeAndFilterAndOrder = async (page: number, size: number, filter?: string, order?: string, ascendingDirection: boolean = false): Promise<[User[], number]> => {
    const direction = ascendingDirection ? 'ASC' : 'DESC';
    const options: FindManyOptions<User> = {
      skip: ((page - 1) * size),
      take: size
    };
    
    if(filter) {
      const fields: FilterFields[] = ['name', 'birthdate', 'username', 'email', { name: 'role', field: 'name' }];
      options.where = makeWhereOptions(filter, fields);
    }

    if(order) {
      switch (order) {
        case 'birthdate':
          options.order = {
            birthdate: direction,
            name: direction,
          }
          break;
        case 'username':
          options.order = {
            username: direction,
          }
          break;
        case 'name':
          options.order = {
            name: direction,
            username: direction,
          }
          break;
        case 'email':
          options.order = {
            email: direction,
          }
          break;
        case 'created':
        default:
          options.order = {
            created: direction,
          }
          break;
      }
    } else {
      options.order = {
        id: direction,
      }
    }

    const users = await this.repository.findAndCount(options);
    const modifiedUsers = users[0].map(user => {
      delete user.password;
      return user;
    });
    return [modifiedUsers, users[1]];
  };

  findOne = async (id: number): Promise<User> => {
    const user = await this.repository.findOneBy({id: id});
    if(!user) {
      throwError('404', `There is no user with id ${id}!`);
    }

    return user;
  };
  
  update = async (id: number, entity: User): Promise<boolean> => {
    const createdUser = this.repository.create(entity);
    if(!createdUser) {
      throwError('400', 'No user is given to save!');
    }
    createdUser.id = undefined;

    const similarEmaiUser = await this.repository.findOneBy({ email: entity?.email});
    if(similarEmaiUser) {
      throwError('400', 'There is already a user with such email!');
    }

    const dbUser = await this.repository.findOneBy({id: id});
    if(!dbUser) {
      throwError('404', `There is no user with id ${id}`);
    }

    if(createdUser.password) {
      const salt = await genSalt();
      const password = await hash(createdUser.password, salt);
      createdUser.password = password;
    }

    const updatedUser = await this.repository.update({ id: id}, createdUser);
    if(!updatedUser || updatedUser.affected < 1) {
      throwError('400', 'Couldn\'t update user!');
    }

    return true;
  };

  remove = async (id: number): Promise<number> => {
    const deactivatedUser = await this.repository.update(
      { id: id }, 
      { active: false});
    if(!deactivatedUser || deactivatedUser.affected < 1) {
      throwError('400', 'Couldn\'t deactivate user!');
    }

    return id;
  };
}