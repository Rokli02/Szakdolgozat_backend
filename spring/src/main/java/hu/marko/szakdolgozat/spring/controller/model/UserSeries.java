package hu.marko.szakdolgozat.spring.controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserSeries {
  private int id;
  private User user;
  private Series series;
  private Status status;
  private int season;
  private int episode;
  private String modification;
}
