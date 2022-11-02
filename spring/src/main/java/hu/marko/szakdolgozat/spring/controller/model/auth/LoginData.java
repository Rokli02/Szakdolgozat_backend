package hu.marko.szakdolgozat.spring.controller.model.auth;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginData implements Serializable {
  private String token;
  private LoginUser user;
}
