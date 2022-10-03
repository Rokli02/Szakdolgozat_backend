import { Like } from 'typeorm';
import { FilterFields } from '../types';

export function makeWhereOptionsForUserSeries(userId: number, filter: string, fields: FilterFields[], status?: number) {
  const where: any[] = fields.map(field => {
    const whereOption: any = {
      user: {
        id: userId
      }
    }

    if(status) {
      whereOption.status = {
        id: status
      }
    }

    if(typeof field === 'object') {
        whereOption.series = {
          [field.name]: {
            [field.field]: Like(`%${filter}%`)
          }
      }
    } else {
      whereOption.series = {
        [field]: Like(`%${filter}%`)
      }
    }

    return whereOption;
  });

  return where;
}

/**
 * 
 * @param filter Olyan szó, amit használni fog az adatbázisban lévő mezőkön keresésre.
 * @param fields Mezők, amiken szeretnénk, hogy a keresés végrehajtódjon.
 * @returns Egy olyan tömbbel tér vissza, amit a "find" metódus "option" paraméterének át tudunk adni.
 */
export function makeWhereOptions(filter: string, fields: FilterFields[]) {
  const where: any[] = fields.map(field => {
    if(typeof field === 'object') {
      return {
        [field.name]: {
          [field.field]: Like(`%${filter}%`)
        }
      }
    } else {
      return {
        [field]: Like(`%${filter}%`)
      }
    }
  });
  
  return where;
}

export function throwError(name: string, message: string) {
  const error = new Error(message);
  error.name = name;

  throw error;
}