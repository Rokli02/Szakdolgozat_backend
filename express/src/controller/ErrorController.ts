import { NextFunction, Request, Response } from 'express';

export function errorHandler(err: Error, req: Request, res: Response, next: NextFunction) {
  if(!err.name) {
    return res.status(500).json({ message: err, reason: 'Couldn\'t find error name!' })
  }

  if(isNaN(parseInt(err.name))) {
    return res.status(400).json({ message: err.message, reason: 'Error name isn\'t a status code!' })
  }

  return res.status(parseInt(err.name)).json({ message: err.message });
}