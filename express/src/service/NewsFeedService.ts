import { NewsFeed } from '../entity/NewsFeed';

export interface iNewsFeedService {
  findByPageAndSizeAndFilterAndOrder(page: number, size: number, filter?: string, order?: string, ascendingDirection?: boolean): Promise<[NewsFeed[], number]>;
  findByUserAndPageAndSizeAndFilterAndOrder(userId: number, page: number, size: number, filter?: string, order?: string, ascendingDirection?: boolean): Promise<[NewsFeed[], number]>
  findOne(id: number): Promise<NewsFeed>;
  save(entity: NewsFeed): Promise<NewsFeed>;
  update(id: number, entity: NewsFeed): Promise<boolean>;
  remove(id: number): Promise<number>;
}