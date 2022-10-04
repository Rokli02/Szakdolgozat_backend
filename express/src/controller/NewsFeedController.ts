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
        console.error('in all newsfeed:\n', err);
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
        console.error('in all newsfeed:\n', err);
        next(err);
    }
  }

  one = async (req: Request<{id: number}>, res: Response, next: NextFunction) => {
    const { id } = req.params;

    try {
        const newsfeeds = await this.service.findOne(id);
        return res.json({newsfeeds});
    } catch(err) {
        console.error('in one newsfeeds:\n', err);
        next(err);
    }
  }

  save = async (req: Request<any, any, NewsFeed>, res: Response, next: NextFunction) => {
    try {
      await this.service.save(req.body);
      return res.status(200).json({ message: 'Saved succesfully!' });
  } catch(err) {
      console.error('in save user:\n', err);
      next(err);
  }
  }

  update = async (req: Request<{id: number}, any, NewsFeed>, res: Response, next: NextFunction) => {
    const { id } = req.params;

    try {
        await this.service.update(id, req.body);
        return res.status(200).json({ message: 'Updated succesfully!' });
    } catch(err) {
        console.error('in update newsfeed:\n', err);
        next(err);
    }
  }

  remove = async (req: Request<{id: number}>, res: Response, next: NextFunction) => {
    const { id } = req.params;

    try {
        await this.service.remove(id);
        return res.status(200).json({ message: 'Deleted succesfully!', id: id });
    } catch(err) {
        console.error('in remove newsfeed:\n', err);
        next(err);
    }
}
}