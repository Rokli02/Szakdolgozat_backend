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
}
