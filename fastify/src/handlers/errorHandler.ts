import { FastifyReply } from 'fastify';
import { QueryFailedError } from 'typeorm';

export function errorHandler(res: FastifyReply, err: Error) {
  console.error(err);
  if(!err.name) {
    return res.status(500).send({ message: err, reason: 'Couldn\'t find error name!' })
  }

  
  switch(err.name) {
    case "JsonWebTokenError":
      return res.status(401).send({ message: "Token is invalid!" });
    case "TokenExpiredError":
      return res.status(401).send({ message: "Token has expired!" });
    case "QueryFailedError":
      if((err as QueryFailedError).driverError.code === "ER_DUP_ENTRY")
        return res.status(400).send({ message: "Duplicate values are not accepted!" });
      if((err as QueryFailedError).driverError.code === "ER_NO_REFERENCED_ROW_2")
        return res.status(400).send({ message: "Can\'t append to a not existing value!" });
      break;
    default:
      break;
  }

  if(isNaN(parseInt(err.name))) {
    return res.status(400).send({ message: err.message, reason: 'Error name isn\'t a status code!' })
  }

  return res.status(parseInt(err.name)).send({ message: err.message });
}