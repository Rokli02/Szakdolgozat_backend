package hu.marko.szakdolgozat.spring.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Image {
  private Long id;
  private String name;
  private String x_offset;
  private String y_offset;
  private String mimeType;
  private String path;

  public Image(hu.marko.szakdolgozat.spring.repository.model.Image image) {
    this.id = image.getId();
    this.name = image.getName();
    this.x_offset = image.getX_offset();
    this.y_offset = image.getY_offset();
  }

  public hu.marko.szakdolgozat.spring.repository.model.Image toEntity() {
    return new hu.marko.szakdolgozat.spring.repository.model.Image(id, name, x_offset, y_offset, null);
  }

  public String getExtensionFromMimeType() {
    switch (mimeType) {
      case "image/jpeg":
        return ".jpg";
      case "image/png":
        return ".png";
      case "image/gif":
        return ".gif";
      case "image/webp":
        return ".webp";
      case "image/svg+xml":
        return ".svg";

      default:
        return "";
    }
  }
}
