import { FastifyInstance, FastifyPluginAsync } from 'fastify'
import tokenValidator from '../../../plugins/tokenValidator'

const statusRoutes: FastifyPluginAsync = async (fastify: FastifyInstance): Promise<void> => {
  fastify.get('/', async function (request, reply) {
    return { statuses: 'every' }
  })
  fastify.register(privateStatusRoutes);
}

async function privateStatusRoutes(fastify: FastifyInstance) {
  await fastify.register(tokenValidator);
  fastify.post('/', async (req, res) => {
    return {new: 'yes', actualy: true};
  })
  .put('/:id', async (req, res) => {
    return {message: 'update', actualy: false};
  })
}

export default statusRoutes;
