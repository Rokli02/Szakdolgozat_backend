package hu.marko.szakdolgozat.spring.controller.model.newModel;

import hu.marko.szakdolgozat.spring.controller.model.Series;
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
}
