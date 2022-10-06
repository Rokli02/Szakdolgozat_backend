import { Repository } from 'typeorm';
import { Category } from '../../entity/Category';
//import mysqlDataSource from '../../data-source';
import { iCategoryService } from '../CategoryService';
import { throwError } from './utils';

export class CategoryService implements iCategoryService {
  private repository: Repository<Category>;
  constructor(repository?: Repository<Category>) {
    this.repository = repository// ? repository : mysqlDataSource.getRepository(Category);
  }

  findAll = async (): Promise<Category[]> => {
    return this.repository.find();
  };


  save = async (entity: Category): Promise<Category> => {
    const createdCategory = this.repository.create(entity);
    if(!createdCategory) {
      throwError('400', 'No category is given to save!');
    }
    createdCategory.id = undefined;

    const savedCategory = await this.repository.save(createdCategory);
    if(!savedCategory) {
      throwError('400', 'Couldn\'t save category!');
    }

    return savedCategory;
  };

  update = async (id: number, entity: Category): Promise<boolean> => {
    const createdCategory = this.repository.create(entity);
    if(!createdCategory) {
      throwError('400', 'No category is given to update!');
    }
    createdCategory.id = id;

    const dbCategory = await this.repository.findOneBy({id: id});
    if(!dbCategory) {
      throwError('404', `There is no category with id ${id}!`);
    }

    const updatedCategory = await this.repository.update({id: id}, createdCategory);
    if(!updatedCategory || updatedCategory.affected < 1) {
      throwError('400', 'Couldn\'t update category!');
    }

    return true;
  };
}