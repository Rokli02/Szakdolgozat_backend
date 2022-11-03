package hu.marko.szakdolgozat.spring.controller.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Role implements Serializable {
  private Long id;
  private String name;

  public Role(hu.marko.szakdolgozat.spring.service.model.Role role) {
    this(role.getId(), role.getName());
  }

  public hu.marko.szakdolgozat.spring.service.model.Role toServiceRole() {
    return new hu.marko.szakdolgozat.spring.service.model.Role(id, name);
  }
}
