package hu.marko.szakdolgozat.spring.service.implementation;

import org.springframework.stereotype.Service;

import hu.marko.szakdolgozat.spring.service.model.PageModel;
import hu.marko.szakdolgozat.spring.service.model.Series;
import hu.marko.szakdolgozat.spring.service.model.UpdateSeries;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class SeriesService implements hu.marko.szakdolgozat.spring.service.SeriesService {
  @Override
  public PageModel<Series> findByPageAndSizeAndFilterAndOrder(int page, int size, String filter, String order,
      boolean ascendingDirection) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Series findOne(Long id) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Series save(Series series) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Boolean update(Long id, UpdateSeries entity) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Boolean deleteImage(Long seriesId) {
    // TODO Auto-generated method stub
    return null;
  }

}
