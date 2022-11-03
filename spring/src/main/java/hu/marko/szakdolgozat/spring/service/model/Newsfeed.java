package hu.marko.szakdolgozat.spring.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Newsfeed {
  private Long id;
  private String title;
  private String description;
  private String modification;
  private Series series;

  public Newsfeed(hu.marko.szakdolgozat.spring.repository.model.Newsfeed newsfeed) {
    this(newsfeed.getId(), newsfeed.getTitle(), newsfeed.getDescription(), newsfeed.getModification().toString(),
        new hu.marko.szakdolgozat.spring.service.model.Series(newsfeed.getSeries()));
  }

  public hu.marko.szakdolgozat.spring.repository.model.Newsfeed toEntity() {
    return new hu.marko.szakdolgozat.spring.repository.model.Newsfeed(id, title, description, null, series.toEntity());
  }
}
