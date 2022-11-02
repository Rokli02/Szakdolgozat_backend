package hu.marko.szakdolgozat.spring.controller.model.auth;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Login {
  @NotBlank
  private String usernameOrEmail;
  @NotBlank
  private String password;
}
