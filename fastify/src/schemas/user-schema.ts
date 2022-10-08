import { FastifySchema } from 'fastify';
import { pageQuerySchema, pageParamsSchema, idSchema, messageSchema } from './schemes'

const roleSchema = {
  type: 'object',
  properties: {
    id: { type: 'integer' },
    name: { type: 'string' }
  },
  required: ['id']
}

const userSchemaIn = {
  type: 'object',
  properties: {
    name: { type: 'string' },
    birthdate: { type: 'string' },
    username: { type: 'string' },
    email: { type: 'string' },
    password: { type: 'string' },
    active: { type: 'boolean' },
    role: roleSchema,
  }
}

const userSchemaOut = {
  type: 'object',
  properties: {
    id: { type: 'integer' },
    name: { type: 'string' },
    birthdate: { type: 'string' },
    username: { type: 'string' },
    email: { type: 'string' },
    active: { type: 'boolean' },
    role: roleSchema,
    created: { type: 'string' }
  }
}

export const allUserSchema: FastifySchema = {
  params: pageParamsSchema,
  querystring: pageQuerySchema,
  response: {
    200: {
      type: 'object',
      properties: {
        users: {
          type: 'array',
          items: userSchemaOut
        },
        count: { type: 'integer' }
      }
    }
  }
}

export const oneUserSchema: FastifySchema = {
  params: idSchema,
  response: {
    200: {
      type: 'object',
      properties: {
        user: userSchemaOut
      }
    }
  }
}

export const updateUserSchema: FastifySchema = {
  params: idSchema,
  body: userSchemaIn,
  response: {
    200: messageSchema
  }
}

export const deleteUserSchema: FastifySchema = {
  params: idSchema,
  response: {
    200: messageSchema
  }
}