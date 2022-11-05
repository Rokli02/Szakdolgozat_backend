import { Newsfeed } from '../entity/Newsfeed';

export interface iNewsFeedService {
  findByPageAndSizeAndFilterAndOrder(page: number, size: number, filter?: string, order?: string, ascendingDirection?: boolean): Promise<[Newsfeed[], number]>;
  findByUserAndPageAndSizeAndFilterAndOrder(userId: number, page: number, size: number, filter?: string, order?: string, ascendingDirection?: boolean): Promise<[Newsfeed[], number]>
  findOne(id: number): Promise<Newsfeed>;
  save(entity: Newsfeed): Promise<Newsfeed>;
  update(id: number, entity: Newsfeed): Promise<boolean>;
  remove(id: number): Promise<number>;
}