package hu.marko.szakdolgozat.spring.service.model;

import java.sql.Date;

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
  private String email;
  private String birthdate;
  private String password;
  private Role role;
  private Date created;
  private Boolean active;

  public User(hu.marko.szakdolgozat.spring.repository.model.User repoUser) {
    this(repoUser.getId(), repoUser.getName(), repoUser.getUsername(),
        repoUser.getEmail(), repoUser.getBirthdate(), repoUser.getPassword(),
        new Role(repoUser.getRole().getId(), repoUser.getRole().getName()),
        repoUser.getCreated(), repoUser.getActive());
  }

  hu.marko.szakdolgozat.spring.repository.model.User toEntity() {
    return new hu.marko.szakdolgozat.spring.repository.model.User(this.id, this.name, this.birthdate, this.username,
        this.email, this.password, this.active, this.created,
        new hu.marko.szakdolgozat.spring.repository.model.Role(this.role.getId(), this.role.getName()));
  }
}