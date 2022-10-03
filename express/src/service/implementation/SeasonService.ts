import { In, Repository } from 'typeorm';
import { Season } from '../../entity/Season';
import mysqlDataSource from '../../data-source';
import { iSeasonService } from '../SeasonService';
import { throwError } from './utils';

export class SeasonService implements iSeasonService {
  private repository: Repository<Season>;
  constructor(repository?: Repository<Season>) {
    this.repository = repository ? repository : mysqlDataSource.getRepository(Season);
  }

  findAllBySeriesid = (seriesId: number): Promise<Season[]> => {
    return this.repository.findBy({series: { id: seriesId }});
  };

  save = async (entities: Season[]): Promise<Season[]> => {
    const createdSeason = this.repository.create(entities);
    if(!createdSeason) {
      throwError('400', 'No season is given to save!');
    }
    createdSeason.forEach((season) => {
      delete season.id;
      return season;
    })

    const savedSeason = await this.repository.save(createdSeason);
    if(!savedSeason) {
      throwError('400', 'Couldn\'t save season!');
    }

    return savedSeason;
  };

  update = async (id: number, entity: Season): Promise<boolean> => {
    const createdSeason = this.repository.create(entity);
    if(!createdSeason) {
      throwError('400', 'No season is given to update!');
    }
    createdSeason.id = id;

    const dbSeason = await this.repository.findOneBy({id: id});
    if(!dbSeason) {
      throwError('404', `There is no season with id ${id}!`);
    }
    
    const updatedSeason = await this.repository.update({id: id}, createdSeason);
    console.log("updatedSeason: ");
    console.log(updatedSeason);
    if(!updatedSeason || updatedSeason.affected < 1) {
      throwError('400', 'Couldn\'t update season!');
    }

    return true;
  };

  updateMultiple = async (entities: Season[]): Promise<boolean> => {
    const createdSeason = this.repository.create(entities);
    if(!createdSeason || createdSeason.length < 0) {
      throwError('400', 'No season is given to update!');
    }
    const ids = entities.map(entity => entity.id);

    const dbSeasons = await this.repository.findBy({id: In(ids)});
    if(!dbSeasons || dbSeasons.length !== ids.length) {
      throwError('404', `There are no seasons with every ids ${ids}!`);
    }
    
    const savedSeasons = await this.repository.save(createdSeason);
    if(!savedSeasons || savedSeasons.length < 0) {
      throwError('400', 'Couldn\'t update seasons!');
    }
    // for(const season of createdSeason) {
    //   const updatedSeason = await this.repository.update({id: season.id}, season);
    //   console.log("updatedSeason: ");
    //   console.log(updatedSeason);
    //   if(!updatedSeason || updatedSeason.affected < 1) {
    //     throwError('400', `Couldn\'t update season with id ${season.id}!`);
    //   }
    // }
    
    return true;
  }

  remove = async (id: number): Promise<number> => {
    const removedSeason = await this.repository.delete({id: id});
    if(!removedSeason || removedSeason.affected < 1) {
      throwError('400', 'Couldn\'t remove season!');
    }

    return id;
  };

  removeMultiple = async (ids: number[]): Promise<number[]> => {
    const removedSeason = await this.repository.delete(ids);
    if(!removedSeason || removedSeason.affected < 1) {
      console.log(removedSeason);
      throwError('400', 'Couldn\'t remove season!');
    }

    return ids;
  }
}