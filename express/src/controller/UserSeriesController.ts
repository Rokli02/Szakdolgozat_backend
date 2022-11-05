import { NextFunction, Request, Response } from 'express';
import { Userseries } from '../entity/Userseries';
import { iUserSeriesService } from '../service/UserSeriesService';

export class UserSeriesController {
  private service: iUserSeriesService;
  constructor(service: iUserSeriesService) {
      this.service = service;
  }

  all = async (req: Request<{ page: number}, any, any, { size: number, filt: string, stat: number, ordr: string, dir: boolean}>, res: Response, next: NextFunction) => {
    const { page } = req.params;
    const { size, filt, ordr, dir } = req.query;
    const userId = req.user.id;
    const stat = isNaN(req.query.stat) ? undefined : Number(req.query.stat);
    
    try {
        const userserieses = await this.service.findByPageAndSizeAndFilterAndStatusAndOrder(userId, page, size, filt, stat, ordr, dir);
        res.json({ serieses: userserieses[0], count: userserieses[1] });
    } catch(err) {
        console.error('in all userseries:\n', err);
        next(err);
    }
  }

  one = async (req: Request<{id: number}>, res: Response, next: NextFunction) => {
    const { id } = req.params;
    const userId = req.user.id;

    try {
        const series = await this.service.findOne(userId, id);
        return res.json({ series });
    } catch(err) {
        next(err);
    }
  }

  save = async (req: Request<any, any, Userseries>, res: Response, next: NextFunction) => {
    const userId = req.user.id;

    try {
        const series = await this.service.save(userId, req.body);
        return res.status(201).json({ series });
    } catch(err) {
        next(err);
    }
  }

  update = async (req: Request<{id: number}, any, Userseries>, res: Response, next: NextFunction) => {
    const { id } = req.params;
    const userId = req.user.id;

    try {
        await this.service.update(userId, id, req.body);
        return res.json({ message: 'Updated succesfully!' });
    } catch(err) {
        next(err);
    }
  }

  remove = async (req: Request<{id: number}>, res: Response, next: NextFunction) => {
    const { id } = req.params;
    const userId = req.user.id;

    try {
        await this.service.remove(userId, id);
        return res.json({ message: 'Deleted succesfully!', id });
    } catch(err) {
        next(err);
    }
}
}