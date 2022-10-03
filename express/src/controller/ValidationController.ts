import { NextFunction, Request, Response } from 'express';

export const hasValidIdParameter = (req: Request<{ id: number }>, res: Response, next: NextFunction) => {
  if(!req.params.id || isNaN(req.params.id) || req.params.id < 0) {
      return res.status(400).json({ message: 'Id must be a number higher than 0!' });
  }

  next();
}

export const hasValidPageAndSize = (req: Request<{ page: number}, any, any, { size: number}>, res: Response, next: NextFunction) => {
  if(!req.params.page || isNaN(req.params.page) || req.params.page < 1) {
    return res.status(400).json({ message: 'Page must be a number higher than 0!' });
  }

  if(!req.query.size || isNaN(req.query.size) || req.query.size < 5) {
      return res.status(400).json({ message: 'Size must be equal or greater number than 5!' });
  }

  next();
}
