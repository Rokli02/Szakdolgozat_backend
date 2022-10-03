import { Router } from 'express';
import { AuthorizationController } from '../controller/AuthorizationController';
import { NewsFeedController } from '../controller/NewsFeedController';
import { hasValidIdParameter, hasValidPageAndSize } from '../controller/ValidationController';
import { AuthorizationSerivce } from '../service/implementation/AuthorizationService';
import { NewsFeedService } from '../service/implementation/NewsfeedService';

export const newsfeedRoutes = () => {
  const router = Router();
  const newsfeedController = new NewsFeedController(new NewsFeedService());
  const auth = new AuthorizationController(new AuthorizationSerivce());

  //Public
  router.get('/page/:page', hasValidPageAndSize, newsfeedController.all);
  router.get('/', newsfeedController.one);
  
  //Private

  //User által követett sorozatok híre

  router.use(auth.verifyToken, auth.hasSiteManagerRight);
  
  router.post('/', newsfeedController.save);
  router.put('/:id', hasValidIdParameter, newsfeedController.update);
  router.delete('/:id', hasValidIdParameter, newsfeedController.remove);

  return router;
}