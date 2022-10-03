import { Season } from '../entity/Season';

export interface iSeasonService {
  findAllBySeriesid(seriesId: number): Promise<Season[]>;
  save(entities: Season[]): Promise<Season[]>;
  update(id: number, entity: Season): Promise<boolean>;
  updateMultiple(entity: Season[]): Promise<boolean>;
  remove(id: number): Promise<number>;
  removeMultiple(id: number[]): Promise<number[]>;
}