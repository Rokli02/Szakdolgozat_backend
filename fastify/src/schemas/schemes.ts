import { FastifySchema } from 'fastify'

export const idSchema = {
  type: 'object',
  properties: {
    id: { type: 'integer' }
  },
  required: ['id']
}

export const messageSchema = {
  type: 'object',
  properties: {
    message: { type: 'string' }
  }
}

export const pageQuerySchema = {
  type: 'object',
  properties: {
    size: { type: 'integer' },
    filt: { type: 'string' },
    ordr: { type: 'string' },
    dir: { type: 'boolean' },
  },
  required: ['size']
}

export const pageParamsSchema = {
  type: 'object',
  properties: {
    page: { type: 'integer' },
  },
  required: ['page']
}

export const response200WithIdSchema: FastifySchema = {
  params: idSchema,
  response: {
    200: messageSchema
  }
}