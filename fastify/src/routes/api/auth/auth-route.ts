import { FastifyPluginAsync } from 'fastify'
import AuthHandler from '../../../handlers/auth-handler'
import { validateNewUserFields } from '../../../handlers/validation-handler';
import { allRoleSchema, loginSchema, signUpSchema } from '../../../schemas/auth-schema';
import { AuthorizationSerivce } from '../../../service/implementation/AuthorizationService'

const authRoutes: FastifyPluginAsync = async (fastify,): Promise<void> => {
  const authHandler: AuthHandler = new AuthHandler(new AuthorizationSerivce());
  fastify.get('/roles', {
    schema: allRoleSchema,
    handler: authHandler.allRole
  })
  .post('/login', {
    schema: loginSchema,
    handler: authHandler.login
  })
  .post('/signup', {
    schema: signUpSchema,
    preHandler: validateNewUserFields,
    handler: authHandler.signup
  })
}

export default authRoutes;
