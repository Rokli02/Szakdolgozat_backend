package hu.marko.szakdolgozat.spring.controller.model.newModel;

import hu.marko.szakdolgozat.spring.controller.model.Series;
import hu.marko.szakdolgozat.spring.service.model.Newsfeed;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NewNewsfeed {
  private String title;
  private String description;
  private Series series;

  public Newsfeed toServiceNewsfeed() {
    return new Newsfeed(null, title, description, null, series.toServiceSeries());
  }
}
