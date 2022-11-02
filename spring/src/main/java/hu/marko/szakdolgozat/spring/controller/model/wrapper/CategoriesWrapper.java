package hu.marko.szakdolgozat.spring.controller.model.wrapper;

import java.util.List;

import hu.marko.szakdolgozat.spring.controller.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriesWrapper {
  private List<Category> categories;
}
