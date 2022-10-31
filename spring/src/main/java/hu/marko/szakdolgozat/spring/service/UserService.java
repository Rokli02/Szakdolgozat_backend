package hu.marko.szakdolgozat.spring.service;

import hu.marko.szakdolgozat.spring.service.model.PageModel;
import hu.marko.szakdolgozat.spring.service.model.User;

public interface UserService {
  PageModel<User> findByPageAndSizeAndFilterAndOrder(int page, int size, String filter, String order,
      boolean ascendingDirection);

  User findOne(int id);

  boolean update(int id, User user);

  int remove(int id);
}
