import { FastifyInstance, FastifyPluginAsync } from 'fastify'
import tokenValidator from '../../../plugins/tokenValidator'

const userRoutes: FastifyPluginAsync = async (fastify: FastifyInstance): Promise<void> => {
  await fastify.register(tokenValidator);

  fastify.get('/page/:page', async function (request, reply) {
    return { pages: { size: 10, pageNum: 1} }
  })
  .get('/:id', async function (request, reply) {
    return { user: { id: 'yes' } }
  })
  .put('/:id', async function (request, reply) {
    return { update: 'do' }
  })
  .delete('/:id', async function (request, reply) {
    return { delete: { id: null } }
  })
}


export default userRoutes;
