import { FastifySchema } from 'fastify';

export const categoryWithId = {
  type: 'object',
  properties: {
    id: { type: 'integer' },
    name: { type: 'string' }
  }
}

/**
 * For all handler
 */
export const categoriesResponse: FastifySchema = {
  response: {
    200: {
      type: 'object',
      properties: {
        categories: {
          type: 'array',
          items: categoryWithId
        }
      }
    }
  }
}

/**
 * For save handler
 */
export const categoryBodyWithoutId: FastifySchema = {
  body: {
    type: 'object',
    properties: {
      name: {type: 'string'},
    },
    required: ['name']
  },
  response: {
    201: categoryWithId
  }
}

/**
 * For update handler
 */
export const categoryBodyWithId: FastifySchema = {
  body: {
    type: 'object',
    properties: {
      name: { type: 'string' }
    },
    required: ['name']
  },
  params: {
    type: 'object',
    properties: {
      id: { type: 'integer' }
    },
    required: ['id']
  },
  response: {
    200: {
      type: 'object',
      properties: {
        message: { type: 'string' }
      }
    }
  }
}