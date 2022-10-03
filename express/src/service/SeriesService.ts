import { Series } from '../entity/Series';

export interface iSeriesService {
  findByPageAndSizeAndFilterAndOrder(page: number, size: number, filter?: string, order?: string, ascendingDirection?: boolean): Promise<[Series[], number]>
  findOne(id: number): Promise<Series>;
  save(entity: Series): Promise<Series>;
  update(id: number, entity: Series): Promise<boolean>;
}