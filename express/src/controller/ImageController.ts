import { Request, Response } from 'express'
import { UploadedFile } from 'express-fileupload';

export class ImageController {
  constructor() {}

  upload = (req: Request, res: Response) => {
    const file: UploadedFile = req.files.image as UploadedFile;
    if(file.truncated) {
      return res.status(400).json({ message: "File is too big to upload!" });
    }

    res.status(201).json({
      name: file.name,
      mimeType: file.mimetype,
      path: file.tempFilePath.split(process.env.TEMP_IMAGE_PATH_SPLIT)[1]
    });
  }
}