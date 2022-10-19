import { NextFunction, Request, Response } from 'express';
import { Category } from '../entity/Category';
import { iCategoryService } from '../service/CategoryService';

export class CategoryController {
  private service: iCategoryService;
  constructor(service: iCategoryService) {
      this.service = service;
  }

  all = async (req: Request, res: Response, next: NextFunction) => {
    try {
      const categories = await this.service.findAll();
      return res.json({categories});
    } catch(err) {
      next(err);
    }
  }

  save = async (req: Request<any, any, Category>, res: Response, next: NextFunction) => {
    try {
      const category = await this.service.save(req.body);
      return res.status(201).json({category});
    } catch(err) {
      next(err);
    }
  }

  update = async (req: Request<{ id: number }, any, Category>, res: Response, next: NextFunction) => {
    const { id } = req.params;

    try {
      await this.service.update(id, req.body);
      return res.json({ message: 'Category update is succesful!'});
    } catch(err) {
      next(err);
    }
  }
}