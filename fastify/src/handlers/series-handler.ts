import { iSeriesService } from '../service/SeriesService';
import { FastifyRequest, FastifyReply } from 'fastify';
import { errorHandler } from './errorHandler';
import { Series } from '../entity/Series';
import { SeriesUpdateDto } from '../service/types';

export class SeriesHandler {
  private service: iSeriesService;
  constructor(service: iSeriesService) {
    this.service = service;
  }

  all = async (req: FastifyRequest<{ Params: { page: number}, Querystring: { size: number, filt: string, ordr: string, dir: boolean}}>, res: FastifyReply) => {
    const { page } = req.params;
    const { size, filt, ordr, dir } = req.query;

    try {
      const serieses = await this.service.findByPageAndSizeAndFilterAndOrder(page, size, filt, ordr, dir);
      return {serieses: serieses[0], count: serieses[1]};
    } catch(err) {
        errorHandler(res, err);
    }
  }

  one = async (req: FastifyRequest<{ Params: { id: number }}>, res: FastifyReply) => {
    const { id } = req.params;

    try {
      const series = await this.service.findOne(id);
      return {series};
    } catch(err) {
      errorHandler(res, err);
    }
  }

  save = async (req: FastifyRequest<{ Body: Series}>, res: FastifyReply) => {
    try {
      const series = await this.service.save(req.body);
      return res.status(201).send({ series });
    } catch(err) {
      errorHandler(res, err);
    }
  }

  update = async (req: FastifyRequest<{ Params: { id: number }, Body: SeriesUpdateDto}>, res: FastifyReply) => {
    const { id } = req.params;

    try {
      await this.service.update(id, req.body);
      return { message: 'Updated succesfully!' };
    } catch(err) {
      errorHandler(res, err);
    }
  }
}