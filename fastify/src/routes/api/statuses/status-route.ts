import { FastifyInstance, FastifyPluginAsync } from 'fastify';
import StatusHandler from '../../../handlers/status-handler';
import hasRight from '../../../plugins/hasRight';
import { allStatusSchema, saveStatusSchema, updateStatusSchema } from '../../../schemas/status-schema';
import { StatusService } from '../../../service/implementation/StatusService';

const statusRoutes: FastifyPluginAsync = async (fastify: FastifyInstance): Promise<void> => {
  const statusHandler: StatusHandler = new StatusHandler(new StatusService());

  fastify.get('/', {
    schema: allStatusSchema,
    handler: statusHandler.all
  })
  fastify.register(privateStatusRoutes, { statusHandler });
}

async function privateStatusRoutes(fastify: FastifyInstance, { statusHandler }: { statusHandler: StatusHandler}) {
  await fastify.register(hasRight, { appropriateRight: ['siteManager', 'admin'] });

  fastify.post('/', {
    schema: saveStatusSchema,
    handler: statusHandler.save
  })
  .put('/:id', {
    schema: updateStatusSchema,
    handler: statusHandler.update
  })
}

export default statusRoutes;
