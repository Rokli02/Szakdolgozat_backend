package hu.marko.szakdolgozat.spring.controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Status {
  private Long id;
  private String name;

  public Status(hu.marko.szakdolgozat.spring.service.model.Status status) {
    this(status.getId(), status.getName());
  }

  public hu.marko.szakdolgozat.spring.service.model.Status toServiceStatus() {
    return new hu.marko.szakdolgozat.spring.service.model.Status(id, name);
  }
}
