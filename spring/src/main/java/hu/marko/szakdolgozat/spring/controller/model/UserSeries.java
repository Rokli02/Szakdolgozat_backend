package hu.marko.szakdolgozat.spring.controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserSeries {
  private Long id;
  private Series series;
  private Status status;
  private Integer season;
  private Integer episode;
  private String modification;

  public UserSeries(hu.marko.szakdolgozat.spring.service.model.UserSeries userSeries) {
    this(userSeries.getId(), new Series(userSeries.getSeries()),
        new Status(userSeries.getStatus()), userSeries.getSeason(),
        userSeries.getEpisode(), userSeries.getModification());
  }

  public hu.marko.szakdolgozat.spring.service.model.UserSeries toServiceUserseries() {
    return new hu.marko.szakdolgozat.spring.service.model.UserSeries(id,
        series != null ? series.toServiceSeries() : null,
        status != null ? status.toServiceStatus() : null,
        season, episode, modification);
  }
}
