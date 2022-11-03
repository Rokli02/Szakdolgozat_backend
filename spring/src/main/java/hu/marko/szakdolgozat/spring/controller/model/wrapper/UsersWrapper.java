package hu.marko.szakdolgozat.spring.controller.model.wrapper;

import java.util.List;

import hu.marko.szakdolgozat.spring.controller.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UsersWrapper {
  private List<User> users;
  private Long count;
}
