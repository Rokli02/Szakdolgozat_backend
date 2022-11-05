package hu.marko.szakdolgozat.spring.controller.model.wrapper;

import java.util.List;

import hu.marko.szakdolgozat.spring.controller.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolesWrapper {
  private List<Role> roles;
}
