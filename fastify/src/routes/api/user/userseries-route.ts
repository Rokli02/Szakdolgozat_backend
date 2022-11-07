import { FastifyInstance, FastifyPluginAsync, RegisterOptions } from 'fastify';
import { UserSeriesHandler } from '../../../handlers/userseries-handler';
import { validateUserSeriesFields } from '../../../handlers/validation-handler';
import hasRight from '../../../plugins/hasRight';
import { response200WithIdSchema } from '../../../schemas/schemes';
import { allUserseriesSchema, oneUserseriesSchema, saveUserseriesSchema, updateUserseriesSchema } from '../../../schemas/userseries-schema';
import { UserSeriesService } from '../../../service/implementation/UserSeriesService';

const userSeriesRoutes: FastifyPluginAsync = async (fastify: FastifyInstance): Promise<void> => {
  const userSeriesHandler: UserSeriesHandler = new UserSeriesHandler(new UserSeriesService());

  fastify.register(privateUserSeriesRoutes, { prefix: '/series', userSeriesHandler});
}

const privateUserSeriesRoutes = async (fastify: FastifyInstance, { userSeriesHandler }: { userSeriesHandler: UserSeriesHandler } & RegisterOptions) => {
  await fastify.register(hasRight, { appropriateRight: ['user'] });

  fastify.get('/page/:page', {
    schema: allUserseriesSchema,
    handler: userSeriesHandler.all
  })
  .get('/:id', {
    schema: oneUserseriesSchema,
    handler: userSeriesHandler.one
  })
  .post('/', {
    schema: saveUserseriesSchema,
    preHandler: validateUserSeriesFields,
    handler: userSeriesHandler.save
  })
  .put('/:id', {
    schema: updateUserseriesSchema,
    preHandler: validateUserSeriesFields,
    handler: userSeriesHandler.update
  })
  .delete('/:id', {
    schema: response200WithIdSchema,
    handler: userSeriesHandler.remove
  })
}


export default userSeriesRoutes;
