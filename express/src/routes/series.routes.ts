import { Router } from 'express';
import { AuthorizationController } from '../controller/AuthorizationController';
import { SeriesController } from '../controller/SeriesController';
import { hasValidIdParameter, hasValidPageAndSize } from '../controller/ValidationController';
import { AuthorizationSerivce } from '../service/implementation/AuthorizationService';
import { SeriesService } from '../service/implementation/SeriesService';

export const seriesRoutes = () => {
  const router = Router();
  const seriesController = new SeriesController(new SeriesService());
  const auth = new AuthorizationController(new AuthorizationSerivce());
  
  //Public
  router.get("/page/:page", hasValidPageAndSize,  seriesController.all);
  router.get("/:id", hasValidIdParameter, seriesController.one);

  //Protected
  router.use(auth.verifyToken, auth.hasSiteManagerRight);
  
  router.post("/", seriesController.save);
  router.put("/:id", hasValidIdParameter, seriesController.update);

  return router;
}