package hu.marko.szakdolgozat.spring.service;

import hu.marko.szakdolgozat.spring.service.model.Newsfeed;
import hu.marko.szakdolgozat.spring.service.model.PageModel;

public interface NewsfeedService {
  PageModel<Newsfeed> findByPageAndSizeAndFilterAndOrder(Integer page, Integer size, String filter, String order,
      Boolean ascendingDirection);

  PageModel<Newsfeed> findByUserAndPageAndSizeAndFilterAndOrder(Long userid, Integer page, Integer size, String filter,
      String order, Boolean ascendingDirection);

  Newsfeed findOne(Long id);

  Newsfeed save(Newsfeed newsfeed);

  Boolean update(Long id, Newsfeed newsfeed);

  Long remove(Long id);
}
