package hu.marko.szakdolgozat.spring.controller.model.newModel;

import hu.marko.szakdolgozat.spring.controller.model.Series;
import hu.marko.szakdolgozat.spring.controller.model.Status;
import hu.marko.szakdolgozat.spring.controller.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewUserSeries {
  private User user;
  private Series series;
  private Status status;
  private int season;
  private int episode;
}
