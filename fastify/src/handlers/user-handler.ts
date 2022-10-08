import { iUserService } from '../service/UserService';
import { FastifyRequest, FastifyReply } from 'fastify';
import { errorHandler } from './errorHandler';
import { User } from '../entity/User';

export class UserHandler {
  private service: iUserService;
  constructor(service: iUserService) {
      this.service = service;
  }

  all = async (req: FastifyRequest<{ Params: { page: number}, Querystring: { size: number, filt: string, ordr: string, dir: boolean}}>, res: FastifyReply) => {
      const { page } = req.params;
      const { size, filt, ordr, dir } = req.query;
      
      try {
          const users = await this.service.findByPageAndSizeAndFilterAndOrder(page, size, filt, ordr, dir);
          return {users: users[0], count: users[1]};
      } catch(err) {
          errorHandler(res, err);
      }
  }

  one = async (req: FastifyRequest<{ Params: {id: number}}>, res: FastifyReply) => {
      const { id } = req.params;

      try {
          const user = await this.service.findOne(id);
          return {user};
      } catch(err) {
          errorHandler(res, err);
      }
  }

  update = async (req: FastifyRequest<{ Params: { id: number }, Body: User}>, res: FastifyReply) => {
      const { id } = req.params;

      try {
          await this.service.update(id, req.body);
          return { message: 'Updated succesfully!' };
      } catch(err) {
          errorHandler(res, err);
      }
  }

  remove = async (req: FastifyRequest<{ Params: {id: number}}>, res: FastifyReply) => {
      const { id } = req.params;

      try {
          await this.service.remove(id);
          return { message: 'Deleted succesfully!', id };
      } catch(err) {
          errorHandler(res, err);
      }
  }
}