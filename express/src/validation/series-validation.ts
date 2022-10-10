import { NextFunction, Request, Response } from 'express';

export const seriesFieldsRequired = (req: Request, res: Response, next: NextFunction) => {
  if(!req.body.title) {
    return res.status(400).json({ message: "Title is required!" });
  }

  if(!req.body.prodYear) {
    return res.status(400).json({ message: "Production year is required!" });
  }

  if(!req.body.ageLimit) {
    return res.status(400).json({ message: "Age limit is required!" });
  }

  if(!req.body.length) {
    return res.status(400).json({ message: "Length is required!" });
  }

  next();
}

export const userSeriesFieldsRequired = (req: Request, res: Response, next: NextFunction) => {
  if(!req.body.status) {
    return res.status(400).json({ message: "Status is required!" });
  }

  if(!req.body.series) {
    return res.status(400).json({ message: "Series is required!" });
  }

  next();
}