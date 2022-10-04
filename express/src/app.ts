import "reflect-metadata";
import * as express from "express";
import * as bodyParser from "body-parser";
import {Request, Response} from "express";
import { errorHandler } from './controller/ErrorController';
import { userRoutes } from './routes/user.routes';
import { userSeriesRoutes } from './routes/userseries.routes';
import { authRoutes } from './routes/auth.routes';
import { config } from 'dotenv';
import { seriesRoutes } from './routes/series.routes';
import { newsfeedRoutes } from './routes/newsfeed.routes';
import { categoryRoutes } from './routes/category.routes';
import { statusRoutes } from './routes/status.routes';
  config();

  const app = express();
  const PORT = process.env.PORT || 5001;
  
  app.use(bodyParser.json());
  app.use('/api/auth', authRoutes());
  app.use('/api/users', userRoutes());
  app.use('/api/user/series', userSeriesRoutes());
  app.use('/api/serieses', seriesRoutes());
  app.use('/api/newsfeeds', newsfeedRoutes());
  app.use('/api/categories', categoryRoutes());
  app.use('/api/statuses', statusRoutes());
  
  app.get('/', (req: Request, res: Response) => {
    res.json({ message: 'Api is available!' });
  });

  app.use(errorHandler);

  app.listen(PORT, () => {
    console.log(`Express server running on port ${PORT}!`);
  });

  //TODO: Validation middleware for saving or updating actions