import { NextFunction, Request, Response } from 'express';
import { QueryFailedError } from 'typeorm';

export function errorHandler(err: Error, req: Request, res: Response, next: NextFunction) {
  if(!err.name) {
    return res.status(500).json({ message: err, reason: 'Couldn\'t find error name!' })
  }

  
  switch(err.name) {
    case "JsonWebTokenError":
      return res.status(401).json({ message: "Token is invalid!" });
    case "TokenExpiredError":
      return res.status(401).json({ message: "Token has expired!" });
    case "QueryFailedError":
      if((err as QueryFailedError).driverError.code === "ER_DUP_ENTRY")
        return res.status(400).json({ message: "Duplicate values are not accepted!" });
      break;
    default:
      break;
  }

  if(isNaN(parseInt(err.name))) {
    return res.status(400).json({ message: err.message, reason: 'Error name isn\'t a status code!' })
  }

  return res.status(parseInt(err.name)).json({ message: err.message });
}