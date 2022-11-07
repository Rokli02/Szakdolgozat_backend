package hu.marko.szakdolgozat.spring.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
import hu.marko.szakdolgozat.spring.service.ImageService;
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
  private ImageService imageService;

  @Override
  public PageModel<Series> findByPageAndSizeAndFilterAndOrder(Integer page, Integer size, String filter, String order,
      Boolean ascendingDirection) {
    Direction direction;
    if (ascendingDirection == null || !ascendingDirection) {
      direction = Direction.DESC;
    } else {
      direction = Direction.ASC;
    }

    String interOrder = "id";
    if (order != null) {
      interOrder = order;
    }

    Page<hu.marko.szakdolgozat.spring.repository.model.Series> pagedEntity;
    String interFilter = "";
    if (filter != null) {
      interFilter = filter;
    }
    pagedEntity = seriesRepository
        .findWithPagination(PageRequest.of(page - 1, size, Sort.by(direction, interOrder)), interFilter);

    Set<Series> seriesList = StreamSupport.stream(pagedEntity.getContent().spliterator(), false)
        .map(Series::new)
        .collect(Collectors.toSet());
    PageModel<Series> pageModel = new PageModel<>(new ArrayList<Series>(seriesList), pagedEntity.getTotalElements());
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

    // Kép áthelyezés ideiglenes helyről
    if (series.getImage() != null) {
      String newImageName = imageService.renameFromTempToPerm(series.getImage());
      entity.getImage().setName(newImageName);
      if (entity.getImage().getX_offset() == null) {
        entity.getImage().setX_offset("0px");
      }
      if (entity.getImage().getY_offset() == null) {
        entity.getImage().setY_offset("0px");
      }
    }

    // Kiszedjük a az évadokat
    List<Season> seasons = entity.getSeasons();
    entity.setSeasons(null);

    // Mentjük a sorozatot
    hu.marko.szakdolgozat.spring.repository.model.Series savedEntity = seriesRepository.save(entity);
    savedEntity.setSeasons(new ArrayList<Season>());

    // Beállítjuk a mentett sorozatot és Mentjük az évadokat
    for (Season ss : seasons) {
      ss.setSeries(savedEntity);
    }

    Iterable<Season> savedSeasons = seasonRepository.saveAll(seasons);

    for (Season ss : savedSeasons) {
      savedEntity.getSeasons().add(ss);
    }

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
          for (int i = 0; i < entity.getSeasons().size(); i++) {
            if (entity.getSeasons().get(i).getId() == sn.getId()) {
              entity.getSeasons().remove(i);
              break;
            }
          }
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
          if (dbCategory.getId() == ct.getId() && ct.getRemove()) {
            category = dbCategory;
            break;
          }
        }

        if (category != null && ct.getRemove()) {
          entity.getCategories().remove(category);
        } else if (category == null && !ct.getRemove()) {
          Optional<Category> oCategory = categoryRepository.findById(ct.getId());
          if (oCategory.isPresent()) {
            entity.getCategories().add(oCategory.get());
          }
        }
      }
    }

    if (series.getImage() != null) {
      // Teljes update
      if (series.getImage().getId() == null) {
        if (entity.getImage() != null) {
          imageService.removeImageFromFolder(entity.getImage().getName());
        }

        String newImageName = imageService.renameFromTempToPerm(series.getImage());
        series.getImage().setName(newImageName);
        entity.setImage(series.getImage().toEntity());
        if (series.getImage().getX_offset() == null || series.getImage().getX_offset().isBlank()) {
          entity.getImage().setX_offset("0px");
        } else {
          entity.getImage().setX_offset(series.getImage().getX_offset());
        }

        if (series.getImage().getY_offset() == null || series.getImage().getY_offset().isBlank()) {
          entity.getImage().setY_offset("0px");
        } else {
          entity.getImage().setY_offset(series.getImage().getY_offset());
        }
      }
      // Részleges update
      else if (entity.getImage() != null) {
        if (series.getImage().getX_offset() == null || series.getImage().getX_offset().isBlank()) {
          entity.getImage().setX_offset("0px");
        } else {
          entity.getImage().setX_offset(series.getImage().getX_offset());
        }

        if (series.getImage().getY_offset() == null || series.getImage().getY_offset().isBlank()) {
          entity.getImage().setY_offset("0px");
        } else {
          entity.getImage().setY_offset(series.getImage().getY_offset());
        }
      }
    }

    hu.marko.szakdolgozat.spring.repository.model.Series savedEntity = seriesRepository.save(entity);
    if (savedEntity != null) {
      return true;
    }
    return false;
  }

  @Override
  public Boolean deleteImage(Long seriesId) {
    Optional<hu.marko.szakdolgozat.spring.repository.model.Series> oSeries = seriesRepository.findById(seriesId);
    if (!oSeries.isPresent()) {
      throw new NotFoundException("There is no such series!");
    }
    hu.marko.szakdolgozat.spring.repository.model.Series entity = oSeries.get();
    String response = imageService.removeImageFromFolder(entity.getImage().getName());

    if (response != null) {
      entity.setImage(null);
      seriesRepository.save(entity);
      return true;
    }

    return false;
  }

}
