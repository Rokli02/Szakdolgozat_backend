import fp from 'fastify-plugin';
import fastifyStatic from '@fastify/static'
import path = require('path');

export default fp(async (fastify) => {
  fastify.register(fastifyStatic, {
    root: path.join(__dirname, process.env.IMAGE_DIR),
    prefix: "/api/images/public",
  })
})