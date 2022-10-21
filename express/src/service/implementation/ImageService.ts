import { iImageService } from '../ImageService';
import { renameSync, unlinkSync } from "fs";
import { UploadableImage } from '../types';
import path = require('path');
import { throwError } from './utils';
import { Repository } from 'typeorm';
import { Image } from '../../entity/Image';
import mysqlDataSource from '../../data-source';

export class ImageService implements iImageService {
  private extensionMap: Map<string, string>;
  private repository: Repository<Image>;
  constructor(repository?: Repository<Image>) {
    this.extensionMap = new Map<string, string>([
      ['image/jpeg', '.jpg'],
      ['image/png', '.png'],
      ['image/gif', '.gif'],
      ['image/webp', '.webp'],
      ['image/svg+xml', '.svg']
    ])
    this.repository = repository ? repository : mysqlDataSource.getRepository(Image);
  }
  renameFromTempToMand = (temp: UploadableImage): string => {
    try {
      const newName = temp.name.slice(0, 1) + Date.now() 
          + temp.name.slice(temp.name.length - 1, temp.name.length) 
          + (this.extensionMap.has(temp.mimeType) ? this.extensionMap.get(temp.mimeType) : '');
      const oldPath = path.join(__dirname, "../../" , process.env.TEMP_IMAGE_DIR) + temp.path;
      const newPath = path.join(__dirname, "../../" ,process.env.IMAGE_DIR) + newName;
      renameSync(oldPath, newPath);

      return newName;
    } catch(err) {
      throwError('400', "Image upload error!");
    }
  };

  removeImageFromDir = (name: string): string => {
    const imagePath = path.join(__dirname, "../../", process.env.IMAGE_DIR) + name;
    try {
      unlinkSync(imagePath);
      return "Image is deleted succesfully!";
    } catch(err) {
      throwError('400', "Couldn\'t delete image!");
    }
  }

  removeImageFromDb = async (name: string) => {
    try {
      const dbImage = await this.repository.findOneBy({ name: name });
      await this.repository.remove(dbImage);
    } catch(err) {
      throwError('400', "Couldn\'t delete image!");
    }
  }
}