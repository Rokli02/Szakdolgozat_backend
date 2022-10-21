import { Image } from '../entity/Image';
import { Role } from '../entity/Role';
import { Season } from '../entity/Season';

export type FilterFields = (string | { name: string, field: string });

export type NewUser = {
  name: string;
  birthdate: string;
  username: string;
  email: string;
  password: string;
}

export type LoginData = {
  token: string,
  user: {
    name: string;
    username: string;
    email: string;
    role: Role;
    created: string;
  }
}

export type ActionSeparatedSeason = {
  saveOrUpdateSeasons: Season[];
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
  image?: Image;
}

export type ActionSeparatedCategory = {
  addCategories: number[],
  removeCategories: number[],
}

export type UploadableImage = {
  name: string;
  mimeType: string,
  path: string;
}