import { FastifySchema } from 'fastify'
import { categoryWithId } from './category-schema'
import { idSchema, pageParamsSchema, pageQuerySchema } from './schemes'

const seasonSchema = {
  type: 'object',
  properties: {
    id: { type: 'integer' },
    season: { type: 'integer' },
    episode: { type: 'integer' },
  }
}

const removableCategorySchema = {
  type: 'object',
  properties: {
    id: { type: 'integer' },
    remove: { type: 'boolean' }
  }
}

const saveCategorySchema = {
  type: 'object',
  properties: {
    id: { type: 'integer' }
  }
}

export const seriesSchemaOut = {
  type: 'object',
  properties: {
    id: { type: 'integer' },
    title: { type: 'string' },
    prodYear: { type: 'integer' },
    ageLimit: { type: 'integer' },
    length: { type: 'integer' },
    seasons: {
      type: 'array',
      items: seasonSchema
    },
    categories: {
      type: 'array',
      items: categoryWithId
    }
  }
}

const seriesSchemaInUpdate = {
  type: 'object',
  properties: {
    id: { type: 'integer' },
    title: { type: 'string' },
    prodYear: { type: 'integer' },
    ageLimit: { type: 'integer' },
    length: { type: 'integer' },
    seasons: {
      type: 'array',
      items: seasonSchema
    },
    categories: {
      type: 'array',
      items: removableCategorySchema
    }
  },
  required: []
}

const seriesSchemaInSave = {
  type: 'object',
  properties: {
    title: { type: 'string' },
    prodYear: { type: 'integer' },
    ageLimit: { type: 'integer' },
    length: { type: 'integer' },
    seasons: {
      type: 'array',
      items: seasonSchema
    },
    categories: {
      type: 'array',
      items: saveCategorySchema
    }
  },
  required: ['title', 'prodYear', 'ageLimit', 'length']
}

export const allSeriesSchema: FastifySchema = {
  params: pageParamsSchema,
  querystring: pageQuerySchema,
  response: {
    200: {
      type: 'object',
      properties: {
        serieses: {
          type: 'array',
          items: seriesSchemaOut
        },
        count: { type: 'integer' }
      }
    }
  }
}

export const oneSeriesSchema: FastifySchema = {
  params: idSchema,
  response: {
    200: {
      type: 'object',
      properties: {
        series: seriesSchemaOut
      }
    }
  }
}

export const saveSeriesSchema: FastifySchema = {
  body: seriesSchemaInSave,
  response: {
    201: {
      type: 'object',
      properties: {
        series: seriesSchemaOut
      }
    }
  }
}

export const updateSeriesSchema: FastifySchema = {
  params: idSchema,
  body: seriesSchemaInUpdate
}