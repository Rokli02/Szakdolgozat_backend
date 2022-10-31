package hu.marko.szakdolgozat.spring.service;

import java.util.List;

import hu.marko.szakdolgozat.spring.service.model.Role;
import hu.marko.szakdolgozat.spring.service.model.User;

public interface AuthorizationService {
  boolean login(String usernameOrEmail, String password);

  boolean signup(User user);

  boolean hasRight(User user, String nameOfRight);

  List<Role> findAllRole();
}
