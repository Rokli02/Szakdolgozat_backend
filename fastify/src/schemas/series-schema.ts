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

const fileOutSchema = {
    type: ['object', 'null'],
    properties: {
      id: { type: 'number' },
      name: { type: 'string' },
      x_offset: { type: 'string' },
      y_offset: { type: 'string' },
    }
}

const fileInSchema = {
  type: ['object', 'null'],
  properties: {
    id: { type: 'number' },
    name: { type: 'string' },
    x_offset: { type: 'string' },
    y_offset: { type: 'string' },
    mimeType: { type: 'string' },
    path: { type: 'string' },
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
    },
    image: fileOutSchema
  }
}

//HIBA FORDULHAT ELŐ AZ IMAGE MIATT
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
    },
    image: fileInSchema,
  }
}

//HIBA FORDULHAT ELŐ AZ IMAGE MIATT
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
    },
    image: fileInSchema,
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