import { FastifyInstance, FastifyPluginAsync, RegisterOptions } from 'fastify';
import NewsFeedHandler from '../../../handlers/newsfeed-handler';
import hasRight from '../../../plugins/hasRight';
import { allNewsFeedSchema, oneNewsFeedSchema, saveNewsFeedSchema, updateNewsFeedSchema } from '../../../schemas/newsfeed-schema';
import { response200WithIdSchema } from '../../../schemas/schemes';
import { NewsFeedService } from '../../../service/implementation/NewsfeedService';

const newsfeedRoutes: FastifyPluginAsync = async (fastify: FastifyInstance): Promise<void> => {
  const newsfeedHandler: NewsFeedHandler = new NewsFeedHandler(new NewsFeedService());
  
  fastify.get('/page/:page', {
    schema: allNewsFeedSchema,
    handler: newsfeedHandler.all
  })
  .get('/:id', {
    schema: oneNewsFeedSchema,
    handler: newsfeedHandler.one
  })
  
  fastify.register(privateUserRoutes, { prefix: 'personal', newsfeedHandler });
  fastify.register(privateSiteManagerRoutes, { prefix: 'edit', newsfeedHandler});
}

async function privateUserRoutes(fastify: FastifyInstance, { newsfeedHandler }: RegisterOptions & { newsfeedHandler: NewsFeedHandler }) {
  await fastify.register(hasRight, { appropriateRight: ['user'] });

  fastify.get('/page/:page', {
    schema: allNewsFeedSchema,
    handler: newsfeedHandler.allPersonal
  })
}
async function privateSiteManagerRoutes(fastify: FastifyInstance, { newsfeedHandler }: RegisterOptions & { newsfeedHandler: NewsFeedHandler }) {
  await fastify.register(hasRight, { appropriateRight: ['siteManager', 'admin'] });

  fastify.post('/', {
    schema: saveNewsFeedSchema,
    handler: newsfeedHandler.save
  })
  .put('/:id', {
    schema: updateNewsFeedSchema,
    handler: newsfeedHandler.update
  })
  .delete('/:id', {
    schema: response200WithIdSchema,
    handler: newsfeedHandler.remove
  })
}

export default newsfeedRoutes;
