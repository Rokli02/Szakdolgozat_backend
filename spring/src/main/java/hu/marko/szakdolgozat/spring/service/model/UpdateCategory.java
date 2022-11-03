package hu.marko.szakdolgozat.spring.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateCategory {
  private Long id;
  private String name;
  private Boolean remove;

  public UpdateCategory(Long id, Boolean remove) {
    this(id, null, remove);
  }

  public UpdateCategory(hu.marko.szakdolgozat.spring.repository.model.Category category) {
    this(category.getId(), category.getName(), false);
  }

  public hu.marko.szakdolgozat.spring.repository.model.Category toEntity() {
    return new hu.marko.szakdolgozat.spring.repository.model.Category(id, name);
  }
}
