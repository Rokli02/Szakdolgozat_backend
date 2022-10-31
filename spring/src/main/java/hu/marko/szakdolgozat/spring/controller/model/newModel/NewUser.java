package hu.marko.szakdolgozat.spring.controller.model.newModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewUser {
  private String name;
  private String username;
  private String email;
  private String birthdate;
  private String password;
}
