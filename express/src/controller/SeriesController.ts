import { NextFunction, Request, Response } from 'express';
import { Series } from '../entity/Series';
import { iSeriesService } from '../service/SeriesService';
import { SeriesUpdateDto } from '../service/types';

export class SeriesController {
  private service: iSeriesService;
  constructor(service: iSeriesService) {
    this.service = service;
  }

  all = async (req: Request<{ page: number}, any, any, { size: number, filt: string, ordr: string, dir: boolean}>, res: Response, next: NextFunction) => {
    const { page } = req.params;
    const { size, filt, ordr, dir } = req.query;

    try {
      const serieses = await this.service.findByPageAndSizeAndFilterAndOrder(page, size, filt, ordr, dir);
      return res.json({serieses: serieses[0], count: serieses[1]});
    } catch(err) {
        next(err);
    }
  }

  one = async (req: Request<{ id: number }>, res: Response, next: NextFunction) => {
    const { id } = req.params;

    try {
      const series = await this.service.findOne(id);
      return res.json({ series });
    } catch(err) {
        next(err);
    }
  }

  save = async (req: Request<any, any, Series>, res: Response, next: NextFunction) => {
    try {
      const series = await this.service.save(req.body);
      return res.status(201).json({ series });
    } catch(err) {
        next(err);
    }
  }

  update = async (req: Request<{ id: number }, any, SeriesUpdateDto>, res: Response, next: NextFunction) => {
    const { id } = req.params;

    try {
      await this.service.update(id, req.body);
      return res.json({ message: 'Updated succesfully!' });
    } catch(err) {
        next(err);
    }
  }

  deleteImage = async (req: Request<{ id: number }, any, SeriesUpdateDto>, res: Response, next: NextFunction) => {
    const { id } = req.params;

    try {
      await this.service.deleteImage(id);
      return res.json({ message: 'Deleted image succesfully!' });
    } catch(err) {
        next(err);
    }
  }
}