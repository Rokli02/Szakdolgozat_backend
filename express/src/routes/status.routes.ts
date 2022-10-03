import { Router } from 'express';
import { AuthorizationController } from '../controller/AuthorizationController';
import {  StatusController } from '../controller/StatusController';
import { hasValidIdParameter } from '../controller/ValidationController';
import { AuthorizationSerivce } from '../service/implementation/AuthorizationService';
import { StatusService } from '../service/implementation/StatusService';

export const statusRoutes = () => {
  const router = Router();
  const statusController = new StatusController(new StatusService());
  const auth = new AuthorizationController(new AuthorizationSerivce());

  //Public
  router.get('/', statusController.all);

  //Private
  router.use(auth.verifyToken, auth.hasSiteManagerRight);

  router.post('/', statusController.save);
  router.put('/:id', hasValidIdParameter, statusController.update);

  return router;
}