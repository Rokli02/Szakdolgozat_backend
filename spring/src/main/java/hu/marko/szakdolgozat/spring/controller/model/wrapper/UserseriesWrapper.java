package hu.marko.szakdolgozat.spring.controller.model.wrapper;

import hu.marko.szakdolgozat.spring.controller.model.UserSeries;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserseriesWrapper {
  private UserSeries series;
}
