import { FastifyInstance, FastifyPluginAsync } from 'fastify'
import { SeriesHandler } from '../../../handlers/series-handler'
import hasRight from '../../../plugins/hasRight'
import tokenValidator from '../../../plugins/tokenValidator'
import { SeriesService } from '../../../service/implementation/SeriesService'

const seriesRoutes: FastifyPluginAsync = async (fastify: FastifyInstance): Promise<void> => {
  const seriesHandler: SeriesHandler = new SeriesHandler(new SeriesService());

  fastify.get('/page/:page', {
    handler: seriesHandler.all
  })
  .get('/:id', {
    handler: seriesHandler.one
  })
  fastify.register(privateSeriesRoutes, { seriesHandler });
}

async function privateSeriesRoutes(fastify: FastifyInstance, { seriesHandler }: { seriesHandler: SeriesHandler}) {
  await fastify.register(tokenValidator);
  await fastify.register(hasRight, { appropriateRight: ['siteManager', 'admin'] })

  fastify.post('/', {
    handler: seriesHandler.save
  })
  .put('/:id', {
    handler: seriesHandler.update
  })
}

export default seriesRoutes;
