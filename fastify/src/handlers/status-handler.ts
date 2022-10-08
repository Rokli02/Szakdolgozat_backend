import { FastifyReply, FastifyRequest } from 'fastify';
import { Status } from '../entity/Status';
import { iStatusService } from '../service/StatusService';
import { errorHandler } from './errorHandler';

export default class StatusHandler {
  private service: iStatusService;
  constructor(service: iStatusService) {
    this.service = service;
  }

  all = async (req: FastifyRequest, res: FastifyReply) => {
    try {
      const statuses = await this.service.findAll();
      return { statuses };
    } catch(err) {
      errorHandler(res, err);
    }
  }

  save = async (req: FastifyRequest<{ Body: Status }>, res: FastifyReply) => {
    try {
      const status = await this.service.save(req.body);
      return res.status(201).send({ status });
    } catch(err) {
      errorHandler(res, err);
    }
  }

  update = async (req: FastifyRequest<{ Params: {id: number}, Body: Status}>, res: FastifyReply) => {
    const { id } = req.params;
      
    try {
      await this.service.update(id, req.body);
      return { message: 'Update is succesful!' };
    } catch(err) {
      errorHandler(res, err);
    }
  }
}