import { NextFunction, Request, Response } from 'express';
import { User } from '../entity/User';
import { iAuthorizationService } from '../service/AuthorizationService';
import { NewUser } from '../service/types';
declare global {
  namespace Express {
    interface Request {
      user: User;
    }
  }
}

export class AuthorizationController {
  private service: iAuthorizationService;
  constructor(service: iAuthorizationService) {
    this.service = service;
  }

  allRole = async (req: Request, res: Response, next: NextFunction) => {
    try {
      const roles = await this.service.findAllRole();
      return res.json(roles);
    } catch(err) {
      console.error('Error in allRole:\n', err);
      return next(err);
    }
  }

  login = async (req: Request<any, any, { usernameOrEmail: string, password: string}>, res: Response, next: NextFunction) => {
    const { usernameOrEmail, password } = req.body;
    
    try {
      const { token, user } = await this.service.login(usernameOrEmail, password);
      return res.json({ token, user });
    } catch(err) {
      console.error('Error in login:\n', err);
      return next(err);
    }
  }

  signup = async (req: Request<any, any , NewUser>, res: Response, next: NextFunction) => {
    const user = req.body;

    try {
      const succes = await this.service.signup(user);
      if(!succes) {
        return res.status(400).json({ message: 'Failed to sign up!' });
      }

      return res.status(201).json({ message: 'Success!' });
    } catch(err) {
      console.error('Error in sign up:\n', err);
      return next(err);
    }
  }

  verifyToken = async (req: Request<{ authorization?: string }>, res: Response, next: NextFunction) => {
    const { authorization } = req.headers;
    if(!authorization) {
      return res.status(401).json({ message: 'You must sign in first!'});
    }

    try {
      const user = await this.service.verifyUserToken(authorization);
      req.user = user;
      next();
    } catch(err) {
      console.error('Error during verify:\n', err);
      return next(err);
    }
  }

  hasUserRight = (req: Request, res: Response, next: NextFunction) => {
    if(!req.user) {
      return res.status(401).json({message: 'Some problem occured during right query!'});
    }

    if(!this.service.hasRight(req.user, 'user')) {
      return res.status(403).json({message: 'You don\'t have permission to access this!'});
    }
    
    next();
  }

  hasAdminRight = (req: Request, res: Response, next: NextFunction) => {
    if(!req.user) {
      return res.status(401).json({message: 'Some problem occured during right query!'});
    }

    if(!this.service.hasRight(req.user, 'admin')) {
      return res.status(403).json({message: 'You don\'t have permission to access this!'});
    }

    next();
  }

  hasSiteManagerRight = (req: Request, res: Response, next: NextFunction) => {
    if(!req.user) {
      return res.status(401).json({message: 'Some problem occured during right query!'});
    }

    if(!this.service.hasRight(req.user, 'siteManager') && 
       !this.service.hasRight(req.user, 'admin')) {
      return res.status(403).json({message: 'You don\'t have permission to access this!'});
    }

    next();
  }
}