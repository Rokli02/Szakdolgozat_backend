package hu.marko.szakdolgozat.spring.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Category {
  private Long id;
  private String name;

  public Category(hu.marko.szakdolgozat.spring.repository.model.Category category) {
    this(category.getId(), category.getName());
  }

  public hu.marko.szakdolgozat.spring.repository.model.Category toEntity() {
    return new hu.marko.szakdolgozat.spring.repository.model.Category(id, name);
  }
}
