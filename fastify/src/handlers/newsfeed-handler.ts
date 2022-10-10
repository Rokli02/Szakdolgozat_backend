import { iNewsFeedService } from '../service/NewsFeedService';
import { FastifyRequest, FastifyReply } from 'fastify';
import { errorHandler } from './errorHandler';
import { NewsFeed } from '../entity/NewsFeed';

export default class NewsFeedHandler {
  private service: iNewsFeedService;
  constructor(service: iNewsFeedService) {
    this.service = service;
  }

  all = async (req: FastifyRequest<{ Params: { page: number}, Querystring: { size: number, filt: string, ordr: string, dir: boolean}}>, res: FastifyReply) => {
    const { page } = req.params;
    const { size, filt, ordr, dir } = req.query;

    try {
      const newsfeeds = await this.service.findByPageAndSizeAndFilterAndOrder(page, size, filt, ordr, dir);
      return {newsfeeds: newsfeeds[0], count: newsfeeds[1]};
    } catch(err) {
        errorHandler(res, err);
    }
  }

  allPersonal = async (req: FastifyRequest<{ Params: { page: number}, Querystring: { size: number, filt: string, ordr: string, dir: boolean}}>, res: FastifyReply) => {
    const { page } = req.params;
    const { size, filt, ordr, dir } = req.query;

    try {
      const newsfeeds = await this.service.findByUserAndPageAndSizeAndFilterAndOrder(req.user?.id, page, size, filt, ordr, dir);
      return {newsfeeds: newsfeeds[0], count: newsfeeds[1]};
    } catch(err) {
        errorHandler(res, err);
    }
  }

  one = async (req:  FastifyRequest<{ Params: { id: number } }>, res: FastifyReply) => {
    const { id } = req.params;

    try {
        const newsfeed = await this.service.findOne(id);
        return { newsfeed };
    } catch(err) {
        errorHandler(res, err);
    }
  }

  save = async (req: FastifyRequest<{ Body: NewsFeed }>, res: FastifyReply) => {
    try {
      const newsfeed = await this.service.save(req.body);
      return res.status(201).send({ newsfeed });
    } catch(err) {
      errorHandler(res, err);
    }
  }

  update = async (req: FastifyRequest<{ Params: { id: number }, Body: NewsFeed }>, res: FastifyReply) => {
    const { id } = req.params;

    try {
        await this.service.update(id, req.body);
        return { message: 'Updated succesfully!' };
    } catch(err) {
        errorHandler(res, err);
    }
  }

  remove = async (req: FastifyRequest<{ Params: { id: number } }>, res: FastifyReply) => {
    const { id } = req.params;

    try {
        await this.service.remove(id);
        return { message: 'Deleted succesfully!', id: id };
    } catch(err) {
        errorHandler(res, err);
    }
  }
}