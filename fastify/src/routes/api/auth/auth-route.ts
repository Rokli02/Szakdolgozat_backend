import { FastifyPluginAsync } from 'fastify'

const authRoutes: FastifyPluginAsync = async (fastify,): Promise<void> => {
  fastify.get('/roles', async function (request, reply) {
    return { roles: true }
  })
  .post('/login', async function (request, reply) {
    return { login: 'success' }
  })
  .post('/signup', async function (request, reply) {
    return { signup: 'new account' }
  })
}

export default authRoutes;
