package hu.marko.szakdolgozat.spring.controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Image {
  private Long id;
  private String name;
  private String mimeType;
  private String path;
  private String x_offset;
  private String y_offset;

  public Image(hu.marko.szakdolgozat.spring.service.model.Image image) {
    id = image.getId();
    name = image.getName();
    x_offset = image.getX_offset();
    y_offset = image.getY_offset();
  }

  public hu.marko.szakdolgozat.spring.service.model.Image toServiceImage() {
    return new hu.marko.szakdolgozat.spring.service.model.Image(id, name, x_offset, y_offset, mimeType, path);
  }
}
