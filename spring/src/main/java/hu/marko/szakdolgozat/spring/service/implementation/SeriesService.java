package hu.marko.szakdolgozat.spring.service.implementation;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import hu.marko.szakdolgozat.spring.exception.BadRequestException;
import hu.marko.szakdolgozat.spring.exception.NotFoundException;
import hu.marko.szakdolgozat.spring.repository.CategoryRepository;
import hu.marko.szakdolgozat.spring.repository.SeasonRepository;
import hu.marko.szakdolgozat.spring.repository.SeriesRepository;
import hu.marko.szakdolgozat.spring.repository.model.Category;
import hu.marko.szakdolgozat.spring.repository.model.Season;
import hu.marko.szakdolgozat.spring.service.model.PageModel;
import hu.marko.szakdolgozat.spring.service.model.Series;
import hu.marko.szakdolgozat.spring.service.model.UpdateCategory;
import hu.marko.szakdolgozat.spring.service.model.UpdateSeries;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class SeriesService implements hu.marko.szakdolgozat.spring.service.SeriesService {
  private SeriesRepository seriesRepository;
  private CategoryRepository categoryRepository;
  private SeasonRepository seasonRepository;

  @Override
  public PageModel<Series> findByPageAndSizeAndFilterAndOrder(Integer page, Integer size, String filter, String order,
      Boolean ascendingDirection) {
    Direction direction;
    if (ascendingDirection == null || !ascendingDirection) {
      direction = Direction.DESC;
    } else {
      direction = Direction.ASC;
    }

    String interFilter = "";
    if (filter != null) {
      interFilter = filter;
    }
    String interOrder = "id";
    if (order != null) {
      interOrder = order;
    }

    Page<hu.marko.szakdolgozat.spring.repository.model.Series> pagedEntity = seriesRepository
        .findWithPagination(PageRequest.of(page - 1, size, Sort.by(direction, interOrder)), interFilter);

    List<Series> seriesList = StreamSupport.stream(pagedEntity.getContent().spliterator(), false).map(Series::new)
        .collect(Collectors.toList());
    PageModel<Series> pageModel = new PageModel<>(seriesList, pagedEntity.getTotalElements());
    return pageModel;
  }

  @Override
  public Series findOne(Long id) {
    Optional<hu.marko.szakdolgozat.spring.repository.model.Series> oSeries = seriesRepository.findById(id);
    if (!oSeries.isPresent()) {
      throw new NotFoundException("There is no series with such ID!");
    }

    return new Series(oSeries.get());
  }

  @Override
  public Series save(Series series) {
    hu.marko.szakdolgozat.spring.repository.model.Series entity = series.toEntity();
    entity.setId(null);
    Iterable<Category> categoryEntities = categoryRepository
        .findAllById(StreamSupport.stream(series.getCategories().spliterator(), false)
            .map((category) -> category.getId()).collect(Collectors.toSet()));

    for (Category ct : categoryEntities) {
      entity.getCategories().add(ct);
    }

    // Kép áthelyezés ideiglenes helyről

    hu.marko.szakdolgozat.spring.repository.model.Series savedEntity = seriesRepository.save(entity);
    return new Series(savedEntity);
  }

  @Override
  public Boolean update(Long id, UpdateSeries series) {
    Optional<hu.marko.szakdolgozat.spring.repository.model.Series> oDbEntity = seriesRepository.findById(id);
    if (!oDbEntity.isPresent()) {
      throw new NotFoundException("There is no series with such ID!");
    }
    hu.marko.szakdolgozat.spring.repository.model.Series entity = oDbEntity.get();

    if (series.getTitle() != null) {
      entity.setTitle(series.getTitle());
    }
    if (series.getProdYear() != null) {
      entity.setProdYear(series.getProdYear());
    }
    if (series.getAgeLimit() != null) {
      entity.setAgeLimit(series.getAgeLimit());
    }
    if (series.getLength() != null) {
      entity.setLength(series.getLength());
    }
    if (series.getAdded() != null) {
      entity.setAdded(series.getAdded());
    }
    if (series.getSeasons() != null && series.getSeasons().size() > 0) {
      // Ellenőrzés
      Iterable<Season> dbSeasons = seasonRepository.findAllById(StreamSupport
          .stream(series.getSeasons().spliterator(), false).map((s) -> s.getId()).collect(Collectors.toList()));
      for (Season sn : dbSeasons) {
        if (sn.getSeries().getId() != id) {
          throw new BadRequestException("Given seasons are owned by another series!");
        }
      }

      for (hu.marko.szakdolgozat.spring.service.model.Season sn : series.getSeasons()) {
        // Ha csak ID van, törölni
        if (sn.getId() != null && sn.getSeason() == null && sn.getEpisode() == null) {
          entity.getSeasons().removeIf((season) -> season.getId() == sn.getId());
          continue;
        }

        // Ha nincs ID, új
        if (sn.getId() == null && sn.getSeason() != null && sn.getEpisode() != null) {
          entity.getSeasons().add(sn.toEntity(entity));
          continue;
        }

        // Ha van ID, akkor már benne van, frissíteni kell
        if (sn.getId() != null && sn.getSeason() != null && sn.getEpisode() != null) {
          for (int i = 0; i < entity.getSeasons().size(); i++) {
            if (entity.getSeasons().get(i).getId() == sn.getId()) {
              entity.getSeasons().set(i, sn.toEntity(entity));
              break;
            }
          }
        }
      }
    }
    if (series.getCategories() != null) {
      for (UpdateCategory ct : series.getCategories()) {
        Category category = null;
        for (Category dbCategory : entity.getCategories()) {
          if (dbCategory.getId() == ct.getId()) {
            category = dbCategory;
            break;
          }
        }

        if (category != null) {
          if (ct.getRemove()) {
            entity.getCategories().remove(category);
          } else {
            entity.getCategories().add(category);
          }
        }
      }
    }

    // Kép áthelyezés ideiglenes helyről

    hu.marko.szakdolgozat.spring.repository.model.Series savedEntity = seriesRepository.save(entity);
    if (savedEntity != null) {
      return true;
    }

    return false;
  }

  @Override
  public Boolean deleteImage(Long seriesId) {
    // TODO: Kép törlés
    return null;
  }

}
