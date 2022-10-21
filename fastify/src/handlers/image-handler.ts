import { FastifyReply, FastifyRequest } from 'fastify';

export class ImageHandler {
  constructor() {}

  upload = (req: FastifyRequest, res: FastifyReply) => {
    // @ts-ignore
    const files = Array(req.raw.files);

    if(!files || files.length < 1) {
      return res.status(400).send({ message: "Error during image upload!" });
    }

    const file = files[0].image;
    res.status(201).send({
      name: file.name,
      mimeType: file.mimetype,
      path: file.tempFilePath.split(process.env.TEMP_IMAGE_PATH_SPLIT)[1]
    });
  }
}