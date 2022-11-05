import { Userseries } from '../entity/Userseries';

export interface iUserSeriesService {
  findByPageAndSizeAndFilterAndStatusAndOrder(userId: number, page: number, size: number, filter?: string, status?: number, order?: string, ascendingDirection?: boolean): Promise<[Userseries[], number]>;
  findOne(userId: number, seriesId: number): Promise<Userseries>;
  save(userId: number, entity: Userseries): Promise<Userseries>;
  update(userId: number, seriesId: number, entity: Userseries): Promise<boolean>;
  remove(userId: number, seriesId: number): Promise<number>;
}