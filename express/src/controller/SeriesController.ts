import { NextFunction, Request, Response } from 'express';
import { Season } from '../entity/Season';
import { Series } from '../entity/Series';
import { iSeasonService } from '../service/SeasonService';
import { iSeriesService } from '../service/SeriesService';

export class SeriesController {
  private service: iSeriesService;
  constructor(service: iSeriesService) {
    this.service = service;
  }

  allSeries = async (req: Request<{ page: number}, any, any, { size: number, filt: string, ordr: string, dir: boolean}>, res: Response, next: NextFunction) => {
    const { page } = req.params;
    const { size, filt, ordr, dir } = req.query;

    try {
      const serieses = await this.service.findByPageAndSizeAndFilterAndOrder(page, size, filt, ordr, dir);
      return res.json({serieses: serieses[0], count: serieses[1]});
    } catch(err) {
        console.error('in all series:\n', err);
        next(err);
    }
  }

  oneSeries = async (req: Request<{ id: number }>, res: Response, next: NextFunction) => {
    const { id } = req.params;

    try {
      const series = await this.service.findOne(id);
      return res.json({series});
    } catch(err) {
        console.error('in one series:\n', err);
        next(err);
    }
  }

  saveSeries = async (req: Request<any, any, Series>, res: Response, next: NextFunction) => {
    try {
      const series = await this.service.save(req.body);
      return res.status(201).json({series});
    } catch(err) {
        console.error('in save series:\n', err);
        next(err);
    }
  }

  updateSeries = async (req: Request<{ id: number }, any, Series>, res: Response, next: NextFunction) => {
    const { id } = req.params;

    try {
      await this.service.update(id, req.body);
      return res.json({ message: 'Updated succesfully!' });
    } catch(err) {
        console.error('in update series:\n', err);
        next(err);
    }
  }

  //
  //  Season metódusok 
  //
/*
  allSeason = async (req: Request<{ id: number }>, res: Response, next: NextFunction) => {
    const { id } = req.params;

    try {
      const seasons = await this.seasonService.findAllBySeriesid(id);
      return res.json({seasons});
    } catch(err) {
      console.error('in all season:\n', err);
      next(err);
    }
  }

  saveSeasons = async (req: Request<any, any, Season>, res: Response, next: NextFunction) => {
    try {
      const season = await this.seasonService.save([req.body]);
      return res.status(201).json({season});
    } catch(err) {
      console.error('in save season:\n', err);
      next(err);
    }
  }

  updateSeason = async (req: Request<{ id: number }, any, Season>, res: Response, next: NextFunction) => {
    const { id } = req.params;

    try {
      await this.seasonService.update(id, req.body);
      return res.json({ message: 'Updated succesfully!' });
    } catch(err) {
      console.error('in update season:\n', err);
      next(err);
    }
  }

  removeSeason = async (req: Request<{ id: number }>, res: Response, next: NextFunction) => {
    const { id } = req.params;

    try {
      await this.seasonService.remove(id);
      return res.json({ message: 'Deleted succesfully!', id });
    } catch(err) {
      console.error('in remove season:\n', err);
      next(err);
    }
  }*/
}