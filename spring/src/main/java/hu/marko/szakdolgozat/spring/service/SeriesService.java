package hu.marko.szakdolgozat.spring.service;

import hu.marko.szakdolgozat.spring.service.model.PageModel;
import hu.marko.szakdolgozat.spring.service.model.Series;
import hu.marko.szakdolgozat.spring.service.model.UpdateSeries;

public interface SeriesService {
  PageModel<Series> findByPageAndSizeAndFilterAndOrder(int page, int size, String filter, String order,
      boolean ascendingDirection);

  Series findOne(int id);

  Series save(Series series);

  boolean update(int id, UpdateSeries entity);

  boolean deleteImage(int seriesId);
}
