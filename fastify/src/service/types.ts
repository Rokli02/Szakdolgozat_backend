import { Season } from '../entity/Season';

export type FilterFields = (string | { name: string, field: string });

export type NewUser = {
  name: string, 
  birthdate: string, 
  username: string, 
  email: string, 
  password: string
}

export type ActionSeparatedSeason = {
  saveSeasons: Season[];
  updateSeasons: Season[];
  deleteSeasons: number[];
}