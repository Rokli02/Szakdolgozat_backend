package hu.marko.szakdolgozat.spring.service;

import java.util.List;

import hu.marko.szakdolgozat.spring.service.model.Role;
import hu.marko.szakdolgozat.spring.service.model.User;

public interface AuthorizationService {
  User validUser(Long id, String username, String email);

  Boolean signup(User user);

  Boolean hasRight(User user, String nameOfRight);

  List<Role> findAllRole();
}
