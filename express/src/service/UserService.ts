import { User } from '../entity/User';

export interface iUserService {
  findByPageAndSizeAndFilterAndOrder(page: number, size: number, filter?: string, order?: string, ascendingDirection?: boolean): Promise<[User[], number]>;
  findOne(id: number): Promise<User>;
  update(id: number, entity: User): Promise<boolean>;
  remove(id: number): Promise<number>;
}