package hu.marko.szakdolgozat.spring.service;

import hu.marko.szakdolgozat.spring.service.model.PageModel;
import hu.marko.szakdolgozat.spring.service.model.Series;
import hu.marko.szakdolgozat.spring.service.model.UpdateSeries;

public interface SeriesService {
  PageModel<Series> findByPageAndSizeAndFilterAndOrder(int page, int size, String filter, String order,
      boolean ascendingDirection);

  Series findOne(Long id);

  Series save(Series series);

  Boolean update(Long id, UpdateSeries entity);

  Boolean deleteImage(Long seriesId);
}
