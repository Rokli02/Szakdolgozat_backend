import { Router } from 'express';
import { AuthorizationController } from '../controller/AuthorizationController';
import { CategoryController } from '../controller/CategoryController';
import { hasValidIdParameter } from '../controller/ValidationController';
import { AuthorizationSerivce } from '../service/implementation/AuthorizationService';
import { CategoryService } from '../service/implementation/CategoryService';

export const categoryRoutes = () => {
  const router = Router();
  const categoryController = new CategoryController(new CategoryService());
  const auth = new AuthorizationController(new AuthorizationSerivce());

  //Public
  router.get('/', categoryController.all);

  //Private
  router.use(auth.verifyToken, auth.hasSiteManagerRight);

  router.post('/', categoryController.save);
  router.put('/:id', hasValidIdParameter, categoryController.update);

  return router;
}