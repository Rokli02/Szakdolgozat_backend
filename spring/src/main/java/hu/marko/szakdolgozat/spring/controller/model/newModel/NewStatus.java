package hu.marko.szakdolgozat.spring.controller.model.newModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewStatus {
  private String name;

  public NewStatus(hu.marko.szakdolgozat.spring.service.model.Status status) {
    this(status.getName());
  }

  public hu.marko.szakdolgozat.spring.service.model.Status toServiceStatus() {
    return new hu.marko.szakdolgozat.spring.service.model.Status(null, name);
  }
}
