import { FastifySchema } from 'fastify';
import { idSchema, pageParamsSchema } from './schemes';
import { seriesSchemaOut } from './series-schema';

const userseriesSchema = {
  type: 'object',
  properties: {
    id: { type: 'integer' },
    season: { type: 'integer' },
    episode: { type: 'integer' },
    series: seriesSchemaOut,
    status: { 
      type: 'object',
      properties: {
        id: { type: 'integer' },
        name: { type: 'string' }
    }}
  }
}

const userseriesPageQuerySchema = {
  type: 'object',
  properties: {
    size: { type: 'integer' },
    filt: { type: 'string' },
    stat: { type: 'integer' },
    ordr: { type: 'string' },
    dir: { type: 'boolean' },
  },
  required: ['size']
}

export const allUserseriesSchema: FastifySchema = {
  params: pageParamsSchema,
  querystring: userseriesPageQuerySchema,
  response: {
    200: {
      type: 'object',
      properties: {
        serieses: {
          type: 'array',
          items: userseriesSchema
        },
        count: { type: 'integer' }
      }
    }
  }
}

export const oneUserseriesSchema: FastifySchema = {
  params: idSchema,
  response: {
    200: {
      type: 'object',
      properties: {
        series: userseriesSchema
      }
    }
  }
}

export const saveUserseriesSchema: FastifySchema = {
  body: userseriesSchema,
  response: {
    200: {
      type: 'object',
      properties: {
        series: userseriesSchema
      },
      required: ['series', 'status']
    }
  }
}

export const updateUserseriesSchema: FastifySchema = {
  params: idSchema,
  body: userseriesSchema
}