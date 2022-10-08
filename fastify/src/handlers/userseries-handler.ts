import { FastifyReply, FastifyRequest } from 'fastify';
import { UserSeries } from '../entity/UserSeries';
import { iUserSeriesService } from '../service/UserSeriesService';
import { errorHandler } from './errorHandler';

export class UserSeriesHandler {
  private service: iUserSeriesService;
  constructor(service: iUserSeriesService) {
      this.service = service;
  }

  all = async (req: FastifyRequest<{ Params: { page: number}, Querystring: { size: number, filt: string, stat: number, ordr: string, dir: boolean}}>, res: FastifyReply) => {
    const { page } = req.params;
    const { size, filt, ordr, dir } = req.query;
    const userId = req.user.id;
    const stat = isNaN(req.query.stat) ? undefined : Number(req.query.stat);
    
    try {
        const userserieses = await this.service.findByPageAndSizeAndFilterAndStatusAndOrder(userId, page, size, filt, stat, ordr, dir);
        return { serieses: userserieses[0], count: userserieses[1] };
    } catch(err) {
        errorHandler(res, err);
    }
  }

  one = async (req: FastifyRequest<{ Params: {id: number}}>, res: FastifyReply) => {
    const { id } = req.params;
    const userId = req.user.id;

    try {
        const series = await this.service.findOne(userId, id);
        return { series };
    } catch(err) {
        errorHandler(res, err);
    }
  }

  save = async (req: FastifyRequest<{ Body: UserSeries }>, res: FastifyReply) => {
    const userId = req.user.id;

    try {
        const series = await this.service.save(userId, req.body);
        return res.status(201).send({series});
    } catch(err) {
        errorHandler(res, err);
    }
  }

  update = async (req: FastifyRequest<{ Params: {id: number}, Body: UserSeries}>, res: FastifyReply) => {
    const { id } = req.params;
    const userId = req.user.id;

    try {
        await this.service.update(userId, id, req.body);
        return { message: 'Updated succesfully!' };
    } catch(err) {
        errorHandler(res, err);
    }
  }

  remove = async (req: FastifyRequest<{ Params: {id: number}}>, res: FastifyReply) => {
    const { id } = req.params;
    const userId = req.user.id;

    try {
        await this.service.remove(userId, id);
        return { message: 'Deleted succesfully!' };
    } catch(err) {
        errorHandler(res, err);
    }
  }
}