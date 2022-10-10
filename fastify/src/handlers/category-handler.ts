import { FastifyReply, FastifyRequest } from 'fastify';
import { Category } from '../entity/Category';
import { errorHandler } from './errorHandler';
import { iCategoryService } from '../service/CategoryService';

export default class CategoryHandler {
  private service: iCategoryService;
  constructor(service: iCategoryService) {
    this.service = service;
  }

  all = async (req: FastifyRequest, res: FastifyReply) => {
    try {
      const categories = await this.service.findAll();
      return { categories };
    } catch(err) {
      return errorHandler(res, err);
    }
  }

  save = async (req: FastifyRequest<{ Body: Category }>, res: FastifyReply) => {
    try {
      const category = await this.service.save(req.body);
      return res.status(201).send({category});
    } catch(err) {
      return errorHandler(res, err);
    }
  }

  update = async (req: FastifyRequest<{ Params: { id: number}, Body: Category }>, res: FastifyReply) => {
    const { id } = req.params;

    try {
      await this.service.update(id, req.body);
      return { message: 'Category update is succesful!'};
    } catch(err) {
      return errorHandler(res, err);
    }
  }
}