package hu.marko.szakdolgozat.spring.controller.model;

import javax.validation.constraints.Email;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
  private Long id;
  private String name;
  private String username;
  @Email
  private String email;
  @DateTimeFormat
  private String birthdate;
  private String password;
  private Role role;
  private String created;
  private boolean active;

  public User(hu.marko.szakdolgozat.spring.service.model.User user) {
    this(user.getId(), user.getName(), user.getUsername(), user.getEmail(), user.getBirthdate(), user.getPassword(),
        new Role(user.getRole()), user.getCreated().toString(), user.getActive());
  }

  public hu.marko.szakdolgozat.spring.service.model.User toServiceUser() {
    return new hu.marko.szakdolgozat.spring.service.model.User(id, name, username, email, birthdate, password,
        role != null ? role.toServiceRole() : null, null, active);
  }
}