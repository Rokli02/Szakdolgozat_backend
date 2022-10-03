import { DataSource } from "typeorm"

const mysqlDataSource = new DataSource({
    type: "mysql",
    host: "localhost",
    port: 3306,
    username: "root",
    password: "",
    database: "test",
    entities: ["./src/entity/*.{js,ts}"],
    migrations: ["./src/migration/**/*.{js,ts}"],
    logging: false,
    synchronize: true,
});

mysqlDataSource.initialize().then(() => {
    console.log('Connected to database Succesfully!');
}).catch((err) => {
    console.log('Couldn\'t connect to the database!');
    throw new Error(err);
});

export default mysqlDataSource;
export { mysqlDataSource };