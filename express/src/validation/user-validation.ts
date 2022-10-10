import { NextFunction, Request, Response } from 'express';

export const newUserFieldsRequired = (req: Request, res: Response, next: NextFunction) => {
  if(!req.body.name) {
    return res.status(400).json({ message: "User name is required!" });
  }

  if(!req.body.birthdate) {
    return res.status(400).json({ message: "Birthdate is required!" });
  }

  if(!req.body.username) {
    return res.status(400).json({ message: "Username is required!" });
  }

  if(!req.body.email) {
    return res.status(400).json({ message: "Email is required!" });
  }

  if(!req.body.password) {
    return res.status(400).json({ message: "Password is required!" });
  }

  next();
}

export const loginFieldsRequired = (req: Request, res: Response, next: NextFunction) => {
  if(!req.body.usernameOrEmail) {
    return res.status(400).json({ message: "Username or email is required!" });
  }

  if(!req.body.password) {
    return res.status(400).json({ message: "Password is required!" });
  }

  next();
}