import { Router } from 'express';
import { AuthorizationController } from '../controller/AuthorizationController';
import { NewsFeedController } from '../controller/NewsFeedController';
import { hasValidIdParameter, hasValidPageAndSize } from '../controller/ValidationController';
import { AuthorizationSerivce } from '../service/implementation/AuthorizationService';
import { NewsFeedService } from '../service/implementation/NewsfeedService';

export const newsfeedRoutes = () => {
  const router = Router();
  const newsfeedController = new NewsFeedController(new NewsFeedService());

  //Public
  router.get('/page/:page', hasValidPageAndSize, newsfeedController.all);
  router.get('/:id', hasValidIdParameter, newsfeedController.one);
  
  //Private
  router.use('/personal', privateUserRoutes(newsfeedController));
  router.use('/edit', privateSiteManagerRoutes(newsfeedController));

  return router;
}

const privateUserRoutes = (newsfeedController: NewsFeedController) => {
  const router = Router();
  const auth = new AuthorizationController(new AuthorizationSerivce());
  router.use(auth.verifyToken, auth.hasUserRight);
  
  router.get('/page/:page', hasValidPageAndSize, newsfeedController.allPersonal);

  return router;
}

const privateSiteManagerRoutes = (newsfeedController: NewsFeedController) => {
  const router = Router();
  const auth = new AuthorizationController(new AuthorizationSerivce());
  router.use(auth.verifyToken, auth.hasSiteManagerRight);

  router.post('/', newsfeedController.save);
  router.put('/:id', hasValidIdParameter, newsfeedController.update);
  router.delete('/:id', hasValidIdParameter, newsfeedController.remove);

  return router;
}