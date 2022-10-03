import { Router } from 'express'
import { AuthorizationController } from '../controller/AuthorizationController';
import { UserController } from '../controller/UserController';
import { hasValidIdParameter, hasValidPageAndSize } from '../controller/ValidationController';
import { AuthorizationSerivce } from '../service/implementation/AuthorizationService';
import { UserService } from '../service/implementation/UserService';

export const userRoutes = () => {
  const router = Router();
  const userController = new UserController(new UserService());
  const auth = new AuthorizationController(new AuthorizationSerivce());
  
  router.use(auth.verifyToken, auth.hasAdminRight);
  
  router.get("/page/:page", hasValidPageAndSize,  userController.all);
  router.get("/:id", hasValidIdParameter, userController.one);
  router.put("/:id", hasValidIdParameter, userController.update);
  router.delete("/:id", hasValidIdParameter, userController.remove);

  return router;
}