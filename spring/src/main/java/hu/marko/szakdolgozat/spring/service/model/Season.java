package hu.marko.szakdolgozat.spring.service.model;

import hu.marko.szakdolgozat.spring.repository.model.Series;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Season {
  private Long id;
  private Integer season;
  private Integer episode;

  public Season(hu.marko.szakdolgozat.spring.repository.model.Season season) {
    this(season.getId(), season.getSeason(), season.getEpisode());
  }

  public hu.marko.szakdolgozat.spring.repository.model.Season toEntity(Series series) {
    return new hu.marko.szakdolgozat.spring.repository.model.Season(id, season, episode, series);
  }
}
