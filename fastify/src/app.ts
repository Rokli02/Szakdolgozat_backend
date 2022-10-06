import 'reflect-metadata'
import Fastify, { fastify } from 'fastify';
import Autoload, { AutoloadPluginOptions } from '@fastify/autoload';
import { join } from 'path';
import { config } from 'dotenv'
import { User } from './entity/User';
config();

const options: Partial<AutoloadPluginOptions> = {}
const app = Fastify({});
const PORT = Number(process.env.PORT) || 5001;

console.log();
app.register(Autoload, {
  dir: join(__dirname, 'plugins/autoload'),
  options: options
});

app.register(Autoload, {
  dir: join(__dirname, 'routes'),
  options: options
});

app.listen({ port: PORT},(err, address) => {
  if(err) {
    app.log.error(err);
    process.exit(1);
  }

  console.log(`Fastify server running on ${address}!`)
});

declare module 'fastify' {
  interface FastifyInstance {
    user: User;
  }
}