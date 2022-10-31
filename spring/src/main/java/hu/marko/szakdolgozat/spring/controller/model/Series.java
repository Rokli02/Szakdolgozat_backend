package hu.marko.szakdolgozat.spring.controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Series {
  private int id;
  private String title;
  private int prodYear;
  private int ageLimit;
  private int length;
  private String added;
  private Season[] seasons;
  private Category categories;
  // private Image image;
}
