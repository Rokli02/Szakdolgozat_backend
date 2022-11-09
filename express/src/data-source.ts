import path = require('path');
import { DataSource } from "typeorm"
import { Category } from './entity/Category';
import { Image } from './entity/Image';
import { Newsfeed } from './entity/Newsfeed';
import { Role } from './entity/Role';
import { Season } from './entity/Season';
import { Series } from './entity/Series';
import { Status } from './entity/Status';
import { User } from './entity/User';
import { Userseries } from './entity/Userseries';

const mysqlDataSource = new DataSource({
    type: "mysql",
    host: process.env.DB_HOST,
    port: Number(process.env.DB_PORT),
    username: process.env.DB_USERNAME,
    password: process.env.DB_PASSWORD,
    database: process.env.DB_NAME,
    entities: [Series, Newsfeed, Season, Status, User, Role, Userseries, Category, Image],
    migrations: [path.join(__dirname,"/migration/**/*.{js,ts}")],
    logging: false,
    synchronize: false,
});


mysqlDataSource.initialize().then(() => {
    console.log('Connected to database succesfully!');
}).catch((err) => {
    console.log('Couldn\'t connect to the database!');
    console.error(err);
    throw new Error(err);
});

export default mysqlDataSource;
export { mysqlDataSource };