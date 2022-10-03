import { NextFunction, Request, Response } from 'express';
import { Status } from '../entity/Status';
import { iStatusService } from '../service/StatusService';

export class StatusController {
  private service: iStatusService;
  constructor(service: iStatusService) {
      this.service = service;
  }

  all = async (req: Request, res: Response, next: NextFunction) => {
    try {
      const statuses = await this.service.findAll();
      return res.json({statuses});
    } catch(err) {
      console.error('in all status:\n', err);
      next(err);
    }
  }

  save = async (req: Request<any, any, Status>, res: Response, next: NextFunction) => {
    try {
      const status = await this.service.save(req.body);
      return res.status(201).json({status});
    } catch(err) {
      console.error('in save status:\n', err);
      next(err);
    }
  }

  update = async (req: Request<{id: number}, any, Status>, res: Response, next: NextFunction) => {
    const { id } = req.params;
      
    try {
      await this.service.update(id, req.body);
      res.json({ message: 'Update is succesful!' });
    } catch(err) {
      console.error('in update status:\n', err);
      next(err);
    }
  }
}