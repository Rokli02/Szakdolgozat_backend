package hu.marko.szakdolgozat.spring.controller.model.wrapper;

import java.util.List;

import hu.marko.szakdolgozat.spring.controller.model.UserSeries;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserseriesesWrapper {
  private List<UserSeries> serieses;
  private Long count;
}
