package hu.marko.szakdolgozat.spring.service;

import org.springframework.web.multipart.MultipartFile;

import hu.marko.szakdolgozat.spring.service.model.Image;
import hu.marko.szakdolgozat.spring.service.model.NewImage;

public interface ImageService {
  NewImage saveToTempFolder(MultipartFile file);

  String renameFromTempToPerm(Image preName);

  String removeImageFromFolder(String name);

  String removeImageFromDb(String name);
}
