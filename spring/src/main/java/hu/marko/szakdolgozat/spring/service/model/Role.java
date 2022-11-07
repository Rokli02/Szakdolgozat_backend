package hu.marko.szakdolgozat.spring.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Role {
  private Long id;
  private String name;

  public Role(hu.marko.szakdolgozat.spring.repository.model.Role role) {
    this(role.getId(), role.getName());
  }

  public hu.marko.szakdolgozat.spring.repository.model.Role toEntity() {
    return new hu.marko.szakdolgozat.spring.repository.model.Role(id, name);
  }
}
