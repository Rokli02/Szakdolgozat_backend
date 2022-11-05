package hu.marko.szakdolgozat.spring.controller.model.auth;

import java.io.Serializable;

import hu.marko.szakdolgozat.spring.controller.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginUser implements Serializable {
  private String name;
  private String username;
  private String email;
  private Role role;
  private String created;
}
