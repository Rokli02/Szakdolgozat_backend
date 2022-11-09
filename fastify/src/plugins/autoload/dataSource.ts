import 'reflect-metadata'
import fp from 'fastify-plugin'
import { DataSource } from 'typeorm';
import { Series } from '../../entity/Series';
import { Newsfeed } from '../../entity/Newsfeed';
import { Season } from '../../entity/Season';
import { Status } from '../../entity/Status';
import { User } from '../../entity/User';
import { Role } from '../../entity/Role';
import { Userseries } from '../../entity/Userseries';
import { Category } from '../../entity/Category';
import { Image } from '../../entity/Image';

let dataSource: DataSource;
export default fp(async (fastify) => {
  dataSource = new DataSource({
    type: "mysql",
    host: process.env.DB_HOST,
    port: Number(process.env.DB_PORT),
    username: process.env.DB_USERNAME,
    password: process.env.DB_PASSWORD,
    database: process.env.DB_NAME,
    entities: [Series, Newsfeed, Season, Status, User, Role, Userseries, Category, Image],//"./src/entity/*.{js,ts}"
    logging: false,
    synchronize: false,
  });
  
  dataSource.initialize().then(() => {
    console.log('Connected to database succesfully!')
  }).catch((err) => {
    console.log('Couldn\'t connect to the database!');
    console.error(err);
    throw new Error(err.message);
  });
});
export { dataSource };
