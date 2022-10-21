import fp from 'fastify-plugin';
import fileUpload from 'fastify-file-upload';
import path = require('path');

export default fp(async (fastify) => {
  fastify.register(fileUpload, {
    limits: {
      files: 1,
      fileSize: 5 * 1024 * 1024
    },
    useTempFiles: true,
    tempFileDir: path.join(__dirname, process.env.TEMP_IMAGE_DIR)
  });
})