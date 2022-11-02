package hu.marko.szakdolgozat.spring.controller.model.newModel;

import hu.marko.szakdolgozat.spring.service.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewCategory {
  private String name;

  public Category toServiceCategory() {
    return new Category(null, name);
  }
}
