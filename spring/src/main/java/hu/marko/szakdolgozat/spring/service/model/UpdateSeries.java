package hu.marko.szakdolgozat.spring.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateSeries {
  private Long id;
  private String title;
  private Integer prodYear;
  private Integer ageLimit;
  private Integer length;
  private String added;
  private Season[] seasons;
  private UpdateCategory categories;
  // private Image image;
}
