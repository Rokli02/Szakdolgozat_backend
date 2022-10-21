import { UploadableImage } from './types';

export interface iImageService {
  renameFromTempToMand(temp: UploadableImage): string;
  removeImageFromDir(name: string): string;
  removeImageFromDb(name: string): Promise<void>;
}