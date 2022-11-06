package hu.marko.szakdolgozat.spring.service.implementation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import hu.marko.szakdolgozat.spring.exception.BadRequestException;
import hu.marko.szakdolgozat.spring.service.model.Image;
import hu.marko.szakdolgozat.spring.service.model.NewImage;

@Service
public class ImageService implements hu.marko.szakdolgozat.spring.service.ImageService {
  @Override
  public NewImage saveToTempFolder(MultipartFile file) {
    if (file == null) {
      throw new BadRequestException("There is no file to upload!");
    }
    if (file.getSize() > 5 * 1024 * 1024) {
      throw new BadRequestException("File is too big to save!");
    }
    NewImage newImage = new NewImage();
    newImage.setMimeType(file.getContentType());

    String ogFileName = file.getOriginalFilename();
    String newName;
    if (ogFileName == null) {
      newName = makeNameFromOriginal(null);
    } else {
      newName = makeNameFromOriginal(ogFileName.split("\\.")[0]);
    }
    newImage.setName(newName);
    newImage.setPath(newName);
    File f = new File("tempImage" + File.separator + newName);

    try {
      OutputStream outputStream = new FileOutputStream(f.getAbsolutePath());
      outputStream.write(file.getInputStream().readAllBytes());

      outputStream.flush();
      outputStream.close();
    } catch (IOException exc) {
      throw new BadRequestException("Something went wrong during fileupload!!");
    }

    return newImage;
  }

  @Override
  public String renameFromTempToPerm(Image preName) {
    File currentDir = new File("");
    File source = new File("tempImage" + File.separator + preName.getName());
    File destination = new File(currentDir.getAbsoluteFile().getParent() +
        File.separator + "images" + File.separator + "public" + File.separator
        + preName.getName() + preName.getExtensionFromMimeType());

    try {
      Path result = Files.move(Paths.get(source.getAbsolutePath()), Paths.get(destination.getAbsolutePath()));

      if (result == null) {
        throw new BadRequestException("File save failed!");
      } else {
        return "File saved succesfully!";
      }
    } catch (IOException exc) {
      throw new BadRequestException("Something went wrong during file save!");
    }
  }

  @Override
  public String removeImageFromFolder(String name) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String removeImageFromDb(String name) {
    // TODO Auto-generated method stub
    return null;
  }

  private String makeNameFromOriginal(String ogName) {
    String openString, closeString;
    Long curTimeMili = System.currentTimeMillis();

    if (ogName == null || ogName.length() <= 1) {
      openString = "pre";
      closeString = "stp";
    } else if (ogName.length() <= 5) {
      openString = String.valueOf(ogName.charAt(0));
      closeString = String.valueOf(ogName.charAt(ogName.length() - 1));
    } else {
      openString = ogName.substring(0, 3);
      closeString = ogName.substring(ogName.length() - 4, ogName.length() - 1);
    }

    return String.format("%s%d%s", openString, curTimeMili, closeString);
  }
}
