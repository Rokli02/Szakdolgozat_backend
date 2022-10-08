import { FastifyInstance, FastifyPluginAsync } from 'fastify'
import hasRight from '../../../plugins/hasRight';
import tokenValidator from '../../../plugins/tokenValidator'

const userSeriesRoutes: FastifyPluginAsync = async (fastify: FastifyInstance): Promise<void> => {
  fastify.register(privateUserSeriesRoutes, { prefix: '/series'});
}

const privateUserSeriesRoutes = async (fastify: FastifyInstance) => {
  await fastify.register(tokenValidator);
  await fastify.register(hasRight, { appropriateRight: ['user'] });

  fastify.get('/page/:page', async function (request, reply) {
    return { pages: { size: 10, pageNum: 1, serieses: 'many'} }
  })
  .get('/:id', async function (request, reply) {
    return { user: { id: 'yes' } }
  })
  .post('/', async function (request, reply) {
    return { update: 'do' }
  })
  .put('/:id', async function (request, reply) {
    return { update: 'do' }
  })
  .delete('/:id', async function (request, reply) {
    return { delete: { id: null } }
  })
}


export default userSeriesRoutes;
