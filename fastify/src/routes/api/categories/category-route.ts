import { FastifyInstance, FastifyPluginAsync } from 'fastify';
import CategoryHandler from '../../../handlers/category-handler';
import hasRight from '../../../plugins/hasRight';
import { categoriesResponse, categoryBodyWithId, categoryBodyWithoutId } from '../../../schemas/category-schema';
import { CategoryService } from '../../../service/implementation/CategoryService';

const categoryRoutes: FastifyPluginAsync = async (fastify: FastifyInstance): Promise<void> => {
  const categoryHandler = new CategoryHandler(new CategoryService);
  fastify.get('/', {
    schema: categoriesResponse,
    handler: categoryHandler.all,
  })

  fastify.register(privateCategoryRoutes, { categoryHandler })
}

const privateCategoryRoutes = async(fastify: FastifyInstance, { categoryHandler }: { categoryHandler: CategoryHandler }) => {
  await fastify.register(hasRight, { appropriateRight: ['siteManager', 'admin'] });

  fastify.post('/', {
    schema: categoryBodyWithoutId,
    handler: categoryHandler.save,
  })
  .put('/:id', {
    schema: categoryBodyWithId,
    handler: categoryHandler.update,
  })
  
}

export default categoryRoutes;
