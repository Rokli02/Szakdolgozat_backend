import "reflect-metadata";
import * as express from "express";
import * as bodyParser from "body-parser";
import {Request, Response} from "express";
import { errorHandler } from './controller/ErrorController';
import { userRoutes } from './routes/user.routes';
import { authRoutes } from './routes/auth.routes';
import { config } from 'dotenv';
import { seriesRoutes } from './routes/series.routes';
  config();

  const app = express();
  const PORT = process.env.PORT || 5001;
  
  app.use(bodyParser.json());
  app.use('/api/auth', authRoutes());
  app.use('/api/users', userRoutes());
  app.use('/api/serieses', seriesRoutes());
  
  app.get('/', (req: Request, res: Response) => {
    res.json({ message: 'Api is available!' });
  });

  app.use(errorHandler);

  app.listen(PORT, () => {
    console.log(`Express server running on port ${PORT}!`);
  });

  //TODO: Validation middleware for saving or updating actions