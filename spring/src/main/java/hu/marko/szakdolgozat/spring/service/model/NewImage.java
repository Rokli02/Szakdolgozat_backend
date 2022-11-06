package hu.marko.szakdolgozat.spring.service.model;

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
}
