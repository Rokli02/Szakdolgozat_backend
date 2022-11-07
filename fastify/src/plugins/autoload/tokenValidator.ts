import { FastifyInstance, FastifyReply, FastifyRequest, HookHandlerDoneFunction } from 'fastify'
import fp from 'fastify-plugin';
import { iAuthorizationService } from '../../service/AuthorizationService';
import { AuthorizationSerivce } from '../../service/implementation/AuthorizationService';
import { errorHandler } from '../../handlers/errorHandler';


export default fp(async (fastify: FastifyInstance) => {
  const authService: iAuthorizationService = new AuthorizationSerivce();
  
  fastify.addHook('preHandler', async (req: FastifyRequest<{Headers: { authorization?: string }}>, res: FastifyReply) => {
    const { authorization } = req.headers;
    
    if(authorization && !req.routerPath.includes("api/auth")) {
      try {
        const user = await authService.verifyUserToken(authorization);
        req.user = user;
      } catch(err) {
        return errorHandler(res, err);
      }
    }
  });
})