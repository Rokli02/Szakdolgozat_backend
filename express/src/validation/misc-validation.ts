import { NextFunction, Request, Response } from 'express';

export const categoryFieldsRequired = (req: Request<{ id: number }>, res: Response, next: NextFunction) => {
  if(!req.body.name) {
    return res.status(400).json({ message: "Category name is required!" });
  }

  next();
}

export const statusFieldsRequired = (req: Request<{ id: number }>, res: Response, next: NextFunction) => {
  if(!req.body.name) {
    return res.status(400).json({ message: "Status name is required!" });
  }

  next();
}