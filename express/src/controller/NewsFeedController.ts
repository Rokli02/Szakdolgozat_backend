import { NextFunction, Request, Response } from 'express';
import { NewsFeed } from '../entity/NewsFeed';
import { iNewsFeedService } from '../service/NewsFeedService';

export class NewsFeedController {
  private service: iNewsFeedService;
  constructor(service: iNewsFeedService) {
    this.service = service;
  }

  all = async (req: Request<{ page: number}, any, any, { size: number, filt: string, ordr: string, dir: boolean}>, res: Response, next: NextFunction) => {
    const { page } = req.params;
    const { size, filt, ordr, dir } = req.query;

    try {
      const newsfeeds = await this.service.findByPageAndSizeAndFilterAndOrder(page, size, filt, ordr, dir);
      res.json({newsfeeds: newsfeeds[0], count: newsfeeds[1]});
    } catch(err) {
        next(err);
    }
  }

  allPersonal = async (req: Request<{ page: number}, any, any, { size: number, filt: string, ordr: string, dir: boolean}>, res: Response, next: NextFunction) => {
    const { page } = req.params;
    const { size, filt, ordr, dir } = req.query;

    try {
      const newsfeeds = await this.service.findByUserAndPageAndSizeAndFilterAndOrder(req.user?.id, page, size, filt, ordr, dir);
      res.json({newsfeeds: newsfeeds[0], count: newsfeeds[1]});
    } catch(err) {
        next(err);
    }
  }

  one = async (req: Request<{id: number}>, res: Response, next: NextFunction) => {
    const { id } = req.params;

    try {
        const newsfeed = await this.service.findOne(id);
        return res.json({ newsfeed });
    } catch(err) {
        next(err);
    }
  }

  save = async (req: Request<any, any, NewsFeed>, res: Response, next: NextFunction) => {
    try {
      await this.service.save(req.body);
      return res.status(201).json({ message: 'Saved succesfully!' });
    } catch(err) {
      next(err);
    }
  }

  update = async (req: Request<{id: number}, any, NewsFeed>, res: Response, next: NextFunction) => {
    const { id } = req.params;

    try {
        await this.service.update(id, req.body);
        return res.json({ message: 'Updated succesfully!' });
    } catch(err) {
        next(err);
    }
  }

  remove = async (req: Request<{id: number}>, res: Response, next: NextFunction) => {
    const { id } = req.params;

    try {
        await this.service.remove(id);
        return res.json({ message: 'Deleted succesfully!', id: id });
    } catch(err) {
        next(err);
    }
  }
}