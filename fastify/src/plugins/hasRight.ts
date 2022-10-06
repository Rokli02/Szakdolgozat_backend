import { FastifyInstance, FastifyRegisterOptions, FastifyReply, FastifyRequest, HookHandlerDoneFunction } from 'fastify';
import fp from 'fastify-plugin';

export default fp(async (fastify: FastifyInstance, opts: { appropriateRight: string[] }) => {
  fastify.addHook('preHandler', async (req: FastifyRequest, res: FastifyReply, done: HookHandlerDoneFunction) => {
    if(!fastify.user) {
      return res.status(401).send({ message: 'You must login first!' });
    }

    const ownedRight = fastify.user.role.name;
    for(let right of opts.appropriateRight) {
      if(right === ownedRight) {
        return done();
      }
    }
    return res.status(403).send({ message: 'You don\'t have permission to access this!' });
  });
  
})