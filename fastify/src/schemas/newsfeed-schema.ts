import { FastifySchema } from 'fastify';
import { idSchema, messageSchema, pageParamsSchema, pageQuerySchema } from './schemes';

const seriesSchema = {
  type: 'object',
  properties: {
    id: { type: 'integer' },
    title: { type: 'string' },
  }
}
const newsfeedSchema = {
  type: 'object',
  properties: {
    id: { type: 'integer' },
    title: { type: 'string' },
    description: { type: 'string' },
    modification: { type: 'string' },
    series: seriesSchema
  }
}

export const allNewsFeedSchema: FastifySchema = {
  querystring: pageQuerySchema,
  params: pageParamsSchema,
  response: {
    200: {
      type: 'object',
      properties: {
        newsfeeds: {
          type: 'array',
          items: newsfeedSchema
        },
        count: { type: 'integer' }
      }
    }
  }
}

export const oneNewsFeedSchema: FastifySchema = {
  params: idSchema,
  response: {
    200: {
      type: 'object',
      properties: {
        newsfeed: newsfeedSchema
      }
    }
  }
}

export const response201Schema: FastifySchema = {
  response: {
    201: messageSchema
  }
}

export const response200WithIdSchema: FastifySchema = {
  params: idSchema,
  response: {
    200: messageSchema
  }
}