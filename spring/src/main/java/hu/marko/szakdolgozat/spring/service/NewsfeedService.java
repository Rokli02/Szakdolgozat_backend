package hu.marko.szakdolgozat.spring.service;

import hu.marko.szakdolgozat.spring.service.model.Newsfeed;
import hu.marko.szakdolgozat.spring.service.model.PageModel;

public interface NewsfeedService {
  PageModel<Newsfeed> findByPageAndSizeAndFilterAndOrder(int page, int size, String filter, String order,
      boolean ascendingDirection);

  PageModel<Newsfeed> findByUserAndPageAndSizeAndFilterAndOrder(Long userid, int page, int size, String filter,
      String order, boolean ascendingDirection);

  Newsfeed findOne(Long id);

  Newsfeed save(Newsfeed newsfeed);

  Boolean update(Long id, Newsfeed newsfeed);

  Long remove(Long id);
}
