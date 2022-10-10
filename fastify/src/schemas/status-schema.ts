import { FastifySchema } from 'fastify'
import { idSchema } from './schemes'

export const statusSchema = {
  type: 'object',
  properties: {
    id: { type: 'integer' },
    name: { type: 'string' }
  },
  required: ['name']
}

export const allStatusSchema: FastifySchema = {
  response: {
    200: {
      type: 'object',
      properties: {
        statuses: {
          type: 'array',
          items: statusSchema
        }
      }
    }
  }
}

export const saveStatusSchema: FastifySchema = {
  body: statusSchema,
  response: {
    201: {
      type: 'object',
      properties: {
        status: statusSchema
      }
    }
  }
}

export const updateStatusSchema: FastifySchema = {
  params: idSchema,
  body: statusSchema
}