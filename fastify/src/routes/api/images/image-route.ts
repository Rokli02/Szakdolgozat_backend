import { FastifyInstance, FastifyPluginAsync } from 'fastify';
import { ImageHandler } from '../../../handlers/image-handler';
import hasRight from '../../../plugins/hasRight';
import tokenValidator from '../../../plugins/tokenValidator';


const imageRoutes: FastifyPluginAsync = async (fastify: FastifyInstance): Promise<void> => {
  const imageHandler: ImageHandler = new ImageHandler();

  fastify.register(privateImageRoutes, { imageHandler });
}

const privateImageRoutes = async(fastify: FastifyInstance, { imageHandler }: { imageHandler: ImageHandler }) => {
  await fastify.register(tokenValidator);
  await fastify.register(hasRight, { appropriateRight: ['siteManager', 'admin'] });

  fastify.post("/", {
    handler: imageHandler.upload,
  })
}

export default imageRoutes;