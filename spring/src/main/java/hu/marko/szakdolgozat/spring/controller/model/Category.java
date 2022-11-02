package hu.marko.szakdolgozat.spring.controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Category {
  private Long id;
  private String name;

  public Category(hu.marko.szakdolgozat.spring.service.model.Category category) {
    this(category.getId(), category.getName());
  }

  public hu.marko.szakdolgozat.spring.service.model.Category toServiceCategory() {
    return new hu.marko.szakdolgozat.spring.service.model.Category(id, name);
  }
}
