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
  router.get("/page/:page", hasValidPageAndSize,  seriesController.allSeries);
  router.get("/:id", hasValidIdParameter, seriesController.oneSeries);
  
  //router.get("/:id/seasons", hasValidIdParameter, seriesController.allSeason);

  //Protected
  router.use(auth.verifyToken, auth.hasSiteManagerRight);
  
  router.post("/", seriesController.saveSeries);
  router.put("/:id", hasValidIdParameter, seriesController.updateSeries);
  /*
  router.post("/seasons", auth.hasSiteManagerRight, seriesController.saveSeasons);
  router.put("/seasons/:id", hasValidIdParameter, seriesController.updateSeason);
  router.delete("/seasons/:id", hasValidIdParameter, seriesController.removeSeason);
  */
  return router;
}