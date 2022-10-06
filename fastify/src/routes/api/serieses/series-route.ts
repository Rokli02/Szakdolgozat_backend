import { FastifyInstance, FastifyPluginAsync } from 'fastify'
import tokenValidator from '../../../plugins/tokenValidator'

const seriesRoutes: FastifyPluginAsync = async (fastify: FastifyInstance): Promise<void> => {
  fastify.get('/page/:page', async function (request, reply) {
    return { pages: 'multiple' }
  })
  .get('/:id', async function (request, reply) {
    return { seriesId: true }
  })
  fastify.register(privateSeriesRoutes);
}

async function privateSeriesRoutes(fastify: FastifyInstance) {
  await fastify.register(tokenValidator);
  fastify.post('/', async (req, res) => {
    return {message: 'yes', actualy: true};
  })
  .put('/:id', async (req, res) => {
    return {message: 'update', actualy: false};
  })
}

export default seriesRoutes;
