import { FastifyInstance, FastifyPluginAsync } from 'fastify'
import tokenValidator from '../../../handlers/tokenValidator'

const seriesRoutes: FastifyPluginAsync = async (fastify,): Promise<void> => {
  fastify.get('/page/:page', async function (request, reply) {
    return { pages: 'multiple' }
  })
  .get('/:id', async function (request, reply) {
    return { seriesId: true }
  })
  .put('/:id', async function (request, reply) {
    return { signup: 'new account' }
  })
  fastify.register(privateSeriesRoutes);
}

async function privateSeriesRoutes(fastify: FastifyInstance) {
  await fastify.register(tokenValidator);
  fastify.post('/', async (req, res) => {
    res.send({message: 'yes', actualy: true});
  })
  .put('/:id', async (req, res) => {
    res.send({message: 'update', actualy: false});
  })
}

export default seriesRoutes;
