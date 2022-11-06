package hu.marko.szakdolgozat.spring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import hu.marko.szakdolgozat.spring.controller.model.newModel.NewImage;
import hu.marko.szakdolgozat.spring.service.ImageService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/images")
public class ImageController {
  private ImageService imageService;

  @PostMapping("")
  public ResponseEntity<NewImage> uploadImage(@RequestBody MultipartFile image) {
    return ResponseEntity.status(HttpStatus.CREATED).body(new NewImage(imageService.saveToTempFolder(image)));
  }
}
