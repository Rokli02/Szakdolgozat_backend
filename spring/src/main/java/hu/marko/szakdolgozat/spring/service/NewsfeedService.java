package hu.marko.szakdolgozat.spring.service;

import hu.marko.szakdolgozat.spring.service.model.Newsfeed;
import hu.marko.szakdolgozat.spring.service.model.PageModel;

public interface NewsfeedService {
  PageModel<Newsfeed> findByPageAndSizeAndFilterAndOrder(int page, int size, String filter, String order,
      boolean ascendingDirection);

  PageModel<Newsfeed> findByUserAndPageAndSizeAndFilterAndOrder(int userid, int page, int size, String filter,
      String order, boolean ascendingDirection);

  Newsfeed findOne(int id);

  Newsfeed save(Newsfeed newsfeed);

  boolean update(int id, Newsfeed newsfeed);

  int remove(int id);
}
