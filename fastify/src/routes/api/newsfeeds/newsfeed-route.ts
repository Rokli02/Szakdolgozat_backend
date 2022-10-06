import { FastifyInstance, FastifyPluginAsync } from 'fastify'
import tokenValidator from '../../../handlers/tokenValidator'

const newsfeedRoutes: FastifyPluginAsync = async (fastify: FastifyInstance): Promise<void> => {
  fastify.get('/page/:page', async function (request, reply) {
    return { pages: {
      many: 'yes',
      pages: 3
    } }
  })
  .get('/:id', async function (request, reply) {
    return { login: 'success' }
  })
  
  fastify.register(privateUserRoutes, { prefix: 'personal' });
  fastify.register(privateSiteManagerRoutes, { prefix: 'edit'});
}

async function privateUserRoutes(fastify: FastifyInstance) {
  await fastify.register(tokenValidator)
    fastify.get('/page/:page', async function (request, reply) {
      return { personal: 'yes', news: 'about serieses' }
    })
}
async function privateSiteManagerRoutes(fastify: FastifyInstance) {
 await fastify.register(tokenValidator)
   fastify.post('/', async function (request, reply) {
     return { post: true }
   })
   .put('/:id', async function (request, reply) {
     return { put: true }
   })
   .delete('/:id', async function (request, reply) {
     return { delete: true }
   })
}

export default newsfeedRoutes;
