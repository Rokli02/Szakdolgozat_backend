import { FastifySchema } from 'fastify';

const roleSchemaWithId = {
  type: 'object',
  properties: {
    id: { type: 'integer' },
    name: { type: 'string' }
  }
}

const newUserSchema = {
  type: 'object',
  properties: {
    name: { type: 'string' },
    birthdate: { type: 'string' },
    username: { type: 'string' },
    email: { type: 'string' },
    password: { type: 'string' }
  },
  required: ['name', 'birthdate', 'username', 'email', 'password']
}
export const allRoleSchema: FastifySchema = {
  response: {
    200: {
      type: 'object',
      properties: {
        roles: {
          type: 'array',
          items: roleSchemaWithId
        }
      }
    }
  }
}

export const signUpSchema: FastifySchema = {
  body: newUserSchema,
  response: {
    201: {
      type: 'object',
      properties: {
        message: { type: 'string' }
      }
    }
  }
}

export const loginSchema: FastifySchema = {
  body: {
    type: 'object',
    properties: {
      usernameOrEmail: { type: 'string' },
      password: { type: 'string' }
    }
  },
  response: {
    200: {
      type: 'object',
      properties: {
        token: { type: 'string' }
      }
    }
  }
}