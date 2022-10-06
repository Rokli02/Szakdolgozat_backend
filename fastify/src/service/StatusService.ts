import { Status } from '../entity/Status';

export interface iStatusService {
  findAll(): Promise<Status[]>;
  save(entity: Status): Promise<Status>;
  update(id: number, entity: Status): Promise<boolean>;
}