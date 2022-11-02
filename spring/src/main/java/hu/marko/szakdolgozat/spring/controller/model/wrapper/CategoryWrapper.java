package hu.marko.szakdolgozat.spring.controller.model.wrapper;

import hu.marko.szakdolgozat.spring.controller.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryWrapper {
  private Category category;
}
