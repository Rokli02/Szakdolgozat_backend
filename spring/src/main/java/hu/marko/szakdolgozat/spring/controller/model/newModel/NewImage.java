package hu.marko.szakdolgozat.spring.controller.model.newModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NewImage {
  private String name;
  private String mimeType;
  private String path;

  public NewImage(hu.marko.szakdolgozat.spring.service.model.NewImage image) {
    name = image.getName();
    mimeType = image.getMimeType();
    path = image.getPath();
  }
}
