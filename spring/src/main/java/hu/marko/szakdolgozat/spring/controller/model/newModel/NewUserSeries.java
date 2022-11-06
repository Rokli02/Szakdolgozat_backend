package hu.marko.szakdolgozat.spring.controller.model.newModel;

import hu.marko.szakdolgozat.spring.controller.model.Series;
import hu.marko.szakdolgozat.spring.controller.model.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewUserSeries {
  private Series series;
  private Status status;
  private Integer season;
  private Integer episode;

  public hu.marko.szakdolgozat.spring.service.model.UserSeries toServiceUserseries() {
    return new hu.marko.szakdolgozat.spring.service.model.UserSeries(null,
        series.toServiceSeries(), status.toServiceStatus(), season, episode, null);
  }
}
