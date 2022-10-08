import { iAuthorizationService } from '../service/AuthorizationService';
import { FastifyRequest, FastifyReply } from 'fastify';
import { errorHandler } from './errorHandler';
import { NewUser } from '../service/types';

export default class AuthHandler {
  private service: iAuthorizationService;
  constructor(service: iAuthorizationService) {
    this.service = service;
  }

  allRole = async (req: FastifyRequest, res: FastifyReply) => {
    try {
      const roles = await this.service.findAllRole();
      return {roles};
    } catch(err) {
      errorHandler(res, err);
    }
  }

  login = async (req: FastifyRequest<{ Body: { usernameOrEmail: string, password: string}}>, res: FastifyReply) => {
    const { usernameOrEmail, password } = req.body;
    
    try {
      const token = await this.service.login(usernameOrEmail, password);
      return { token };
    } catch(err) {
      errorHandler(res, err);
    }
  }

  signup = async (req: FastifyRequest<{ Body: NewUser}>, res: FastifyReply) => {
    const user = req.body;

    try {
      const succes = await this.service.signup(user);
      if(!succes) {
        return res.status(400).send({ message: 'Failed to sign up!' });
      }

      return res.status(201).send({ message: 'Success!' });
    } catch(err) {
      errorHandler(res, err);
    }
  }
}