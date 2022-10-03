import { Season } from '../entity/Season';

export type SeasonDto = {
  id: number;
  season: number;
  episode: number;
  action: 'save' | 'update' | 'delete';
}

export type ActionSeparatedSeason = {
  saveSeason: Season[];
  updateSeason: Season[];
  deleteSeason: Season[];
}