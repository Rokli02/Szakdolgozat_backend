import 'reflect-metadata'
import fp from 'fastify-plugin'
import { DataSource } from 'typeorm';

let dataSource: DataSource;
export default fp(async (fastify) => {
  dataSource = new DataSource({
    type: "mysql",
    host: process.env.DB_HOST,
    port: Number(process.env.DB_PORT),
    username: process.env.DB_USERNAME,
    password: process.env.DB_PASSWORD,
    database: process.env.DB_NAME,
    entities: ["./src/entity/*.{js,ts}"],//
    logging: false,
    synchronize: true,
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
