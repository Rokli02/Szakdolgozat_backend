import { Router } from 'express'
import { AuthorizationController } from '../controller/AuthorizationController';
import { AuthorizationSerivce } from '../service/implementation/AuthorizationService';

export const authRoutes = () => {
  const router = Router();
  const auth = new AuthorizationController(new AuthorizationSerivce());

  router.get('/roles', auth.allRole);
  router.post('/login', auth.login);
  router.post('/signup', auth.signup);

  return router;
}