package hu.marko.szakdolgozat.spring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/images")
public class ImageController {

  // TODO: Megcsinálni a képfeltöltést
  @PostMapping("")
  public ResponseEntity<String> uploadImage() {
    return null;
  }
}
