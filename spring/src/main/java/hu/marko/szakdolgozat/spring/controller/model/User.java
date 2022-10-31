package hu.marko.szakdolgozat.spring.controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
  private int id;
  private String name;
  private String username;
  private String email;
  private String birthdate;
  private String password;
  private Role role;
  private String created;
  private boolean active;
}