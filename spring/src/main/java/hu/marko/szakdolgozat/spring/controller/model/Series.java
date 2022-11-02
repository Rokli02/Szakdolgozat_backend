package hu.marko.szakdolgozat.spring.controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Series {
  private Long id;
  private String title;
  private Integer prodYear;
  private Integer ageLimit;
  private Integer length;
  private String added;
  private Season[] seasons;
  private Category categories;
  // private Image image;
}
