import { UserSeries } from '../entity/UserSeries';

export interface iUserSeriesService {
  findByPageAndSizeAndFilterAndStatusAndOrder(userId: number, page: number, size: number, filter?: string, status?: number, order?: string, ascendingDirection?: boolean): Promise<[UserSeries[], number]>;
  findOne(userId: number, seriesId: number): Promise<UserSeries>;
  save(userId: number, entity: UserSeries): Promise<UserSeries>;
  update(userId: number, seriesId: number, entity: UserSeries): Promise<boolean>;
  remove(userId: number, seriesId: number): Promise<number>;
}