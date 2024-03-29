import { FastifyInstance, FastifyPluginAsync } from 'fastify';
import { SeriesHandler } from '../../../handlers/series-handler';
import { validateSeriesFields } from '../../../handlers/validation-handler';
import hasRight from '../../../plugins/hasRight';
import { response200WithIdSchema } from '../../../schemas/schemes';
import { allSeriesSchema, oneSeriesSchema, saveSeriesSchema, updateSeriesSchema } from '../../../schemas/series-schema';
import { SeriesService } from '../../../service/implementation/SeriesService';

const seriesRoutes: FastifyPluginAsync = async (fastify: FastifyInstance): Promise<void> => {
  const seriesHandler: SeriesHandler = new SeriesHandler(new SeriesService());

  fastify.get('/page/:page', {
    schema: allSeriesSchema,
    handler: seriesHandler.all
  })
  .get('/:id', {
    schema: oneSeriesSchema,
    handler: seriesHandler.one
  })
  fastify.register(privateSeriesRoutes, { seriesHandler });
}

async function privateSeriesRoutes(fastify: FastifyInstance, { seriesHandler }: { seriesHandler: SeriesHandler}) {
  await fastify.register(hasRight, { appropriateRight: ['siteManager', 'admin'] })

  fastify.post('/', {
    schema: saveSeriesSchema,
    preHandler: validateSeriesFields,
    handler: seriesHandler.save
  })
  .put('/:id', {
    schema: updateSeriesSchema,
    preHandler: validateSeriesFields,
    handler: seriesHandler.update
  })
  .delete('/image/:id', {
    schema: response200WithIdSchema,
    handler: seriesHandler.deleteImage
  })
}

export default seriesRoutes;
