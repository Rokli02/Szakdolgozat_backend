import { NextFunction, Request, Response } from 'express';

export const newfeedFieldsRequired = (req: Request, res: Response, next: NextFunction) => {
  if(!req.body.title) {
    return res.status(400).json({ message: "Title is required!" });
  }

  if(!req.body.description) {
    return res.status(400).json({ message: "Title is required!" });
  }

  next();
}

export const newsfeedFieldsValid = (req: Request<{ id: number }>, res: Response, next: NextFunction) => {
  if(req.body.title && req.body.title.length < 1) {
    return res.status(400).json({ message: "Title must be valid!" });
  }

  if(req.body.description && req.body.description.length < 1) {
    return res.status(400).json({ message: "Description must be valid!" });
  }

  next();
}