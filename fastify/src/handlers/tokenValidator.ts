import { FastifyInstance, FastifyReply, FastifyRequest, HookHandlerDoneFunction } from 'fastify'
import fp from 'fastify-plugin';
import { iAuthorizationService } from '../service/AuthorizationService';
import { AuthorizationSerivce } from '../service/implementation/AuthorizationService';
import { errorHandler } from './errorHandler';


export default fp(async (fastify: FastifyInstance) => {
  const authService: iAuthorizationService = new AuthorizationSerivce();
  
  fastify.addHook('preHandler', async (req: FastifyRequest<{Headers: { Authorization?: string }}>, res: FastifyReply, done: HookHandlerDoneFunction) => {
    const { Authorization } = req.headers;
    if(!Authorization) {
      return res.status(401).send({ message: 'There is no authorization token!' });
    }

    try {
      const user = await authService.verifyUserToken(Authorization);
      fastify.user = user;
    } catch(err) {
      return errorHandler(req, res, err);
    }
    fastify.user = null;
  });
  
})