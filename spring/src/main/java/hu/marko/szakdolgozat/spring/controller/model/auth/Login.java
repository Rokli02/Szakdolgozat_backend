package hu.marko.szakdolgozat.spring.controller.model.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Login {
  private String usernameOrEmail;
  private String password;
}
