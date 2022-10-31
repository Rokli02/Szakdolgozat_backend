package hu.marko.szakdolgozat.spring.controller.model.updateModel;

import hu.marko.szakdolgozat.spring.controller.model.Season;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateSeries {
  private int id;
  private String title;
  private int prodYear;
  private int ageLimit;
  private int length;
  private String added;
  private Season[] seasons;
  private UpdateCategory categories;
  // private Image image;
}
