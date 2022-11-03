package hu.marko.szakdolgozat.spring.controller.model.wrapper;

import hu.marko.szakdolgozat.spring.controller.model.Newsfeed;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsfeedWrapper {
  private Newsfeed newsfeed;
}
