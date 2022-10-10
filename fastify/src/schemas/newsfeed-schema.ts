import { FastifySchema } from 'fastify';
import { idSchema, messageSchema, pageParamsSchema, pageQuerySchema } from './schemes';

const seriesSchema = {
  type: 'object',
  properties: {
    id: { type: 'integer' },
    title: { type: 'string' },
  }
}
const getNewsfeedSchema = {
  type: 'object',
  properties: {
    id: { type: 'integer' },
    title: { type: 'string' },
    description: { type: 'string' },
    modification: { type: 'string' },
    series: seriesSchema
  }
}

const saveNewsfeedSchema = {
  type: 'object',
  properties: {
    id: { type: 'integer' },
    title: { type: 'string' },
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
          items: getNewsfeedSchema
        },
        count: { type: 'integer' }
      }
    }
  }
}

export const updateNewsFeedSchema: FastifySchema = {
  params: idSchema,
  body: saveNewsfeedSchema,
}

export const oneNewsFeedSchema: FastifySchema = {
  params: idSchema,
  response: {
    200: {
      type: 'object',
      properties: {
        newsfeed: getNewsfeedSchema
      }
    }
  }
}

export const saveNewsFeedSchema: FastifySchema = {
  body: saveNewsfeedSchema,
  response: {
    201: {
      type: 'object',
      properties: {
        newsfeed: getNewsfeedSchema
      },
      required: ['title', 'description']
    }
  }
}
