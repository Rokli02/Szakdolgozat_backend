import { Season } from '../entity/Season';

export type FilterFields = (string | { name: string, field: string });

export type NewUser = {
  name: string;
  birthdate: string;
  username: string;
  email: string;
  password: string;
}

export type ActionSeparatedSeason = {
  saveSeasons: Season[];
  updateSeasons: Season[];
  deleteSeasons: number[];
}

export type CategoryDto = {
  id: number;
  remove: boolean;
}

export type SeriesUpdateDto = {
  id: number;
  title: string;
  prodYear: number;
  ageLimit: number;
  length: number;
  seasons: Season[];
  categories: CategoryDto[];
}

export type ActionSeparatedCategory = {
  addCategories: number[],
  removeCategories: number[],
}