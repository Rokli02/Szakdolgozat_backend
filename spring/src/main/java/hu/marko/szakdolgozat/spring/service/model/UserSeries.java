package hu.marko.szakdolgozat.spring.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserSeries {
  private Long id;
  private User user;
  private Series series;
  private Status status;
  private Integer season;
  private Integer episode;
  private String modification;
}
