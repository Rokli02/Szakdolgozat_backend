package hu.marko.szakdolgozat.spring.controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Newsfeed {
  private int id;
  private String title;
  private String description;
  private String modification;
  private Series series;
}
