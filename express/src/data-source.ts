import { DataSource } from "typeorm"

const mysqlDataSource = new DataSource({
    type: "mysql",
    host: process.env.DB_HOST,
    port: Number(process.env.DB_PORT),
    username: process.env.DB_USERNAME,
    password: process.env.DB_PASSWORD,
    database: process.env.DB_NAME,
    entities: ["./src/entity/*.{js,ts}"],
    migrations: ["./src/migration/**/*.{js,ts}"],
    logging: false,
    synchronize: true,
});

mysqlDataSource.initialize().then(() => {
    console.log('Connected to database succesfully!');
}).catch((err) => {
    console.log('Couldn\'t connect to the database!');
    throw new Error(err);
});

export default mysqlDataSource;
export { mysqlDataSource };