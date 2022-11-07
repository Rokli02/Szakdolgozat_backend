import { FastifyInstance, FastifyRegisterOptions, FastifyReply, FastifyRequest, HookHandlerDoneFunction } from 'fastify';
import fp from 'fastify-plugin';

export default fp(async (fastify: FastifyInstance, opts: { appropriateRight: string[] }) => {
  fastify.addHook('preHandler', async (req: FastifyRequest, res: FastifyReply) => {
    if(!req.user) {
      return res.status(401).send({ message: 'You must to login first!' });
    }

    const ownedRight = req.user.role.name;
    for(let right of opts.appropriateRight) {
      if(right === ownedRight) {
        return;
      }
    }
    return res.status(403).send({ message: 'You don\'t have permission to access this!' });
  });
  
})