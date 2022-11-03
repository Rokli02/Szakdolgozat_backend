package hu.marko.szakdolgozat.spring.service;

import hu.marko.szakdolgozat.spring.service.model.PageModel;
import hu.marko.szakdolgozat.spring.service.model.User;

public interface UserService {
  PageModel<User> findByPageAndSizeAndFilterAndOrder(Integer page, Integer size, String filter, String order,
      Boolean ascendingDirection);

  User findOne(Long id);

  Boolean update(Long id, User user);

  Long remove(Long id);
}
