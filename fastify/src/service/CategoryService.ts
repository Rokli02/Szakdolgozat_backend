import { Category } from '../entity/Category';

export interface iCategoryService {
  findAll(): Promise<Category[]>;
  save(entity: Category): Promise<Category>;
  update(id: number, entity: Category): Promise<boolean>;
}