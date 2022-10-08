import 'reflect-metadata'
import { config } from 'dotenv'
config();
import Fastify from 'fastify';
import Autoload, { AutoloadPluginOptions } from '@fastify/autoload';
import { join } from 'path';
import { User } from './entity/User';

const options: Partial<AutoloadPluginOptions> = {}
const app = Fastify({});
const PORT = Number(process.env.PORT) || 5001;

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
  interface FastifyRequest {
    user: User;
  }
}