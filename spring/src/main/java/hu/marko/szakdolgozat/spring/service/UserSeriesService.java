package hu.marko.szakdolgozat.spring.service;

import hu.marko.szakdolgozat.spring.service.model.PageModel;
import hu.marko.szakdolgozat.spring.service.model.UserSeries;

public interface UserSeriesService {
  PageModel<UserSeries> findByPageAndSizeAndFilterAndStatusAndOrder(Long userId, Integer page, Integer size,
      String filter,
      Long status, String order, Boolean ascendingDirection);

  UserSeries findOne(Long userId, Long seriesId);

  UserSeries save(Long userId, UserSeries entity);

  Boolean update(Long userId, Long seriesId, UserSeries entity);

  Long remove(Long userId, Long seriesId);
}
