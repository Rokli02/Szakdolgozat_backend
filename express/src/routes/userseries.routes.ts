import { Router } from 'express';
import { AuthorizationController } from '../controller/AuthorizationController';
import { UserSeriesController } from '../controller/UserSeriesController';
import { hasValidIdParameter, hasValidPageAndSize } from '../controller/ValidationController';
import { AuthorizationSerivce } from '../service/implementation/AuthorizationService';
import { UserSeriesService } from '../service/implementation/UserSeriesService';

export const userRoutes = () => {
  const router = Router();
  const userseriesController = new UserSeriesController(new UserSeriesService());
  const auth = new AuthorizationController(new AuthorizationSerivce());
  
  router.use(auth.verifyToken, auth.hasUserRight);

  router.get("/page/:page", hasValidPageAndSize, userseriesController.all);
  router.get("/:id", hasValidIdParameter, userseriesController.one);
  router.post("/", userseriesController.save); //Validáló metódus
  router.put("/:id", hasValidIdParameter, userseriesController.update);
  router.delete("/:id", hasValidIdParameter, userseriesController.remove);

  return router;
}