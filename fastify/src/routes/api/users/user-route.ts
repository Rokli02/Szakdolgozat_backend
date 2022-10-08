import { FastifyInstance, FastifyPluginAsync } from 'fastify'
import { UserHandler } from '../../../handlers/user-handler';
import hasRight from '../../../plugins/hasRight';
import tokenValidator from '../../../plugins/tokenValidator'
import { allUserSchema, deleteUserSchema, oneUserSchema, updateUserSchema } from '../../../schemas/user-schema';
import { UserService } from '../../../service/implementation/UserService';

const userRoutes: FastifyPluginAsync = async (fastify: FastifyInstance): Promise<void> => {
  await fastify.register(tokenValidator);
  await fastify.register(hasRight, { appropriateRight: ['admin'] })

  const userHandler: UserHandler = new UserHandler(new UserService());

  fastify.get('/page/:page', {
    schema: allUserSchema,
    handler: userHandler.all
  })
  .get('/:id', {
    schema: oneUserSchema,
    handler: userHandler.one
  })
  .put('/:id', {
    schema: updateUserSchema,
    handler: userHandler.update
  })
  .delete('/:id', {
    schema: deleteUserSchema,
    handler: userHandler.remove
  })
}


export default userRoutes;
