package hu.marko.szakdolgozat.spring.controller.model.auth;

import hu.marko.szakdolgozat.spring.controller.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginUser {
  private String name;
  private String username;
  private String email;
  private Role role;
  private String created;
}
