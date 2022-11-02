package hu.marko.szakdolgozat.spring.service;

import hu.marko.szakdolgozat.spring.service.model.PageModel;
import hu.marko.szakdolgozat.spring.service.model.UserSeries;

public interface UserSeriesService {
  PageModel<UserSeries> findByPageAndSizeAndFilterAndStatusAndOrder(Long userId, int page, int size, String filter,
      int status, String order, boolean ascendingDirection);

  UserSeries findOne(Long userId, Long seriesId);

  UserSeries save(Long userId, UserSeries entity);

  Boolean update(Long userId, Long seriesId, UserSeries entity);

  Long remove(Long userId, Long seriesId);
}
