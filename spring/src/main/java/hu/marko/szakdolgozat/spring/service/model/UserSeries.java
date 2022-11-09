package hu.marko.szakdolgozat.spring.service.model;

import hu.marko.szakdolgozat.spring.repository.model.Userseries;
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

  public UserSeries(Userseries userseries) {
    this(userseries.getId(), new Series(userseries.getSeries()),
        new Status(userseries.getStatus()), userseries.getSeason(), userseries.getEpisode(),
        userseries.getModification() != null ? userseries.getModification().toString().split(" ")[0] : null);
  }

  public Userseries toEntity() {
    return new Userseries(id, null,
        series != null ? series.toEntity() : null,
        status != null ? status.toEntity() : null,
        season, episode, null);
  }
}
