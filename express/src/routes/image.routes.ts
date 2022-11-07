import { Router } from 'express';
import { AuthorizationController } from '../controller/AuthorizationController';
import { ImageController } from '../controller/ImageController';
import { AuthorizationSerivce } from '../service/implementation/AuthorizationService';

export const imageRoutes = () => {
  const router = Router();
  const imageController = new ImageController();
  const auth = new AuthorizationController(new AuthorizationSerivce());

  //Protected routes
  router.use(auth.hasSiteManagerRight);

  router.post("/", imageController.upload);

  return router;
}