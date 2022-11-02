package hu.marko.szakdolgozat.spring.controller.model.newModel;

import hu.marko.szakdolgozat.spring.controller.model.Category;
import hu.marko.szakdolgozat.spring.controller.model.Season;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NewSeries {
  private String title;
  private Integer prodYear;
  private Integer ageLimit;
  private Integer length;
  private Season[] seasons;
  private Category categories;
  // private Image image;
}
