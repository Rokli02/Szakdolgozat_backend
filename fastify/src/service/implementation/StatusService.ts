import { Repository } from 'typeorm';
import { Status } from '../../entity/Status';
import { dataSource } from '../../plugins/autoload/dataSource';
import { iStatusService } from '../StatusService';
import { throwError } from './utils';

export class StatusService implements iStatusService {
  private repository: Repository<Status>;
  constructor(repository?: Repository<Status>) {
    this.repository = repository ? repository : dataSource.getRepository(Status);
  }

  findAll = async (): Promise<Status[]> => {
    return this.repository.find();
  };

  save = async (entity: Status): Promise<Status> => {
    const createdStatus = this.repository.create(entity);
    if(!createdStatus) {
      throwError('400', 'No status is given to save!');
    }
    createdStatus.id = undefined;

    const savedStatus = await this.repository.save(createdStatus);
    if(!savedStatus) {
      throwError('400', 'Couldn\'t save status!');
    }

    return savedStatus;
  };

  update = async (id: number, entity: Status): Promise<boolean> => {
    const createdStatus = this.repository.create(entity);
    if(!createdStatus) {
      throwError('400', 'No status is given to update!');
    }
    createdStatus.id = id;

    const dbStatus = await this.repository.findOneBy({id: id});
    if(!dbStatus) {
      throwError('404', `There is no status with id ${id}!`);
    }

    const updatedStatus = await this.repository.update({id: id}, createdStatus);
    if(!updatedStatus ||updatedStatus.affected < 1) {
      throwError('400', 'Couldn\'t update status!');
    }

    return true;
  };
}