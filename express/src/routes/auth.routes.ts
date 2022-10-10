import { Router } from 'express'
import { AuthorizationController } from '../controller/AuthorizationController';
import { AuthorizationSerivce } from '../service/implementation/AuthorizationService';
import { loginFieldsRequired, newUserFieldsRequired } from '../validation/user-validation';
import { validateNewUserFields } from '../validation/validation-middleware';

export const authRoutes = () => {
  const router = Router();
  const auth = new AuthorizationController(new AuthorizationSerivce());

  router.get('/roles', auth.allRole);
  router.post('/login', loginFieldsRequired, auth.login);
  router.post('/signup', newUserFieldsRequired, validateNewUserFields, auth.signup);

  return router;
}