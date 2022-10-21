import "reflect-metadata";
import { config } from 'dotenv';
config();
import * as express from "express";
import * as bodyParser from "body-parser";
import {Request, Response} from "express";
import { errorHandler } from './controller/ErrorController';
import { userRoutes } from './routes/user.routes';
import { userSeriesRoutes } from './routes/userseries.routes';
import { authRoutes } from './routes/auth.routes';
import { seriesRoutes } from './routes/series.routes';
import { newsfeedRoutes } from './routes/newsfeed.routes';
import { categoryRoutes } from './routes/category.routes';
import { statusRoutes } from './routes/status.routes';
import * as fileUpload from "express-fileupload";
import * as cors from 'cors';
import path = require('path');
import { imageRoutes } from './routes/image.routes';

  const app = express();
  const PORT = process.env.PORT || 5001;
  
  app.use(express.json());
  app.use(cors());
  app.use(fileUpload({ 
    limits: {
      files: 1,
      fileSize: 5 * 1024 * 1024
    },
    useTempFiles: true,
    tempFileDir: path.join(__dirname, process.env.TEMP_IMAGE_DIR)
  }));
  
  app.use("/api/images/public" ,express.static(path.join(__dirname, process.env.IMAGE_DIR)));
  app.use('/api/auth', authRoutes());
  app.use('/api/users', userRoutes());
  app.use('/api/user/series', userSeriesRoutes());
  app.use('/api/serieses', seriesRoutes());
  app.use('/api/newsfeeds', newsfeedRoutes());
  app.use('/api/categories', categoryRoutes());
  app.use('/api/statuses', statusRoutes());
  app.use('/api/images', imageRoutes());
  
  app.get('/', (req: Request, res: Response) => {
    res.json({ message: 'Api is available!' });
  });
  
  app.use(errorHandler);

  app.listen(PORT, () => {
    console.log(`Express server running on http://localhost:${PORT}!`);
  });