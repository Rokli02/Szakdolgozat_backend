import { FastifyPluginAsync } from 'fastify'

const categoryRoutes: FastifyPluginAsync = async (fastify,): Promise<void> => {
  fastify.get('/', async function (request, reply) {
    return { categories: 'all' }
  })
  .post('/', async function (request, reply) {
    return { login: 'success' }
  })
  .put('/:id', async function (request, reply) {
    return { signup: 'new account' }
  })
}

export default categoryRoutes;
