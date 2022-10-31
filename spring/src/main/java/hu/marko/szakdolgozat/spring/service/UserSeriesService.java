package hu.marko.szakdolgozat.spring.service;

import hu.marko.szakdolgozat.spring.service.model.PageModel;
import hu.marko.szakdolgozat.spring.service.model.UserSeries;

public interface UserSeriesService {
  PageModel<UserSeries> findByPageAndSizeAndFilterAndStatusAndOrder(int userId, int page, int size, String filter,
      int status, String order, boolean ascendingDirection);

  UserSeries findOne(int userId, int seriesId);

  UserSeries save(int userId, UserSeries entity);

  boolean update(int userId, int seriesId, UserSeries entity);

  int remove(int userId, int seriesId);
}
