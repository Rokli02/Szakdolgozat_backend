import { Router } from 'express';
import { AuthorizationController } from '../controller/AuthorizationController';
import { CategoryController } from '../controller/CategoryController';
import { hasValidIdParameter } from '../controller/ValidationController';
import { AuthorizationSerivce } from '../service/implementation/AuthorizationService';
import { CategoryService } from '../service/implementation/CategoryService';
import { categoryFieldsRequired } from '../validation/misc-validation';

export const categoryRoutes = () => {
  const router = Router();
  const categoryController = new CategoryController(new CategoryService());
  const auth = new AuthorizationController(new AuthorizationSerivce());

  //Public
  router.get('/', categoryController.all);

  //Private
  router.use(auth.hasSiteManagerRight);

  router.post('/', categoryFieldsRequired, categoryController.save);
  router.put('/:id', hasValidIdParameter, categoryFieldsRequired, categoryController.update);

  return router;
}