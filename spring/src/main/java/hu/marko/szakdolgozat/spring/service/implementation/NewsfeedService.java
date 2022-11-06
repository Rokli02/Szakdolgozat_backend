package hu.marko.szakdolgozat.spring.service.implementation;

import org.springframework.data.domain.Sort.Direction;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import hu.marko.szakdolgozat.spring.exception.NotFoundException;
import hu.marko.szakdolgozat.spring.repository.NewsfeedRepository;
import hu.marko.szakdolgozat.spring.repository.SeriesRepository;
import hu.marko.szakdolgozat.spring.repository.UserRepository;
import hu.marko.szakdolgozat.spring.repository.model.Series;
import hu.marko.szakdolgozat.spring.repository.model.User;
import hu.marko.szakdolgozat.spring.service.model.Newsfeed;
import hu.marko.szakdolgozat.spring.service.model.PageModel;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class NewsfeedService implements hu.marko.szakdolgozat.spring.service.NewsfeedService {
  private NewsfeedRepository newsfeedRepository;
  private SeriesRepository seriesRepository;
  private UserRepository userRepository;

  @Override
  public PageModel<Newsfeed> findByPageAndSizeAndFilterAndOrder(Integer page, Integer size, String filter, String order,
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
      interOrder = getOrder(order);
    }

    Page<hu.marko.szakdolgozat.spring.repository.model.Newsfeed> pagedEntity = newsfeedRepository
        .findWithPagination(PageRequest.of(page - 1, size, Sort.by(direction, interOrder)), interFilter);

    List<Newsfeed> newsfeedList = StreamSupport.stream(pagedEntity.getContent().spliterator(), false).map(Newsfeed::new)
        .collect(Collectors.toList());
    PageModel<Newsfeed> pageModel = new PageModel<>(newsfeedList, pagedEntity.getTotalElements());
    return pageModel;
  }

  @Override
  public PageModel<Newsfeed> findByUserAndPageAndSizeAndFilterAndOrder(Long userid, Integer page, Integer size,
      String filter, String order, Boolean ascendingDirection) {
    System.out.println("\n\nUserId:\n" + userid);
    Optional<User> oUser = userRepository.findById(userid);
    if (!oUser.isPresent()) {
      throw new NotFoundException("Valami hiba lépett fel, jelentkezz be újra!");
    }

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
      interOrder = getOrder(order);
    }

    Page<hu.marko.szakdolgozat.spring.repository.model.Newsfeed> pagedEntity = newsfeedRepository
        .findPersonalWithPagination(PageRequest.of(page - 1, size, Sort.by(direction, interOrder)), interFilter,
            userid);

    List<Newsfeed> newsfeedList = StreamSupport.stream(pagedEntity.getContent().spliterator(), false).map(Newsfeed::new)
        .collect(Collectors.toList());
    PageModel<Newsfeed> pageModel = new PageModel<>(newsfeedList, pagedEntity.getTotalElements());
    return pageModel;
  }

  @Override
  public Newsfeed findOne(Long id) {
    Optional<hu.marko.szakdolgozat.spring.repository.model.Newsfeed> oNewsfeed = newsfeedRepository.findById(id);
    if (!oNewsfeed.isPresent()) {
      throw new NotFoundException("There is no newsfeed with such ID!");
    }

    return new Newsfeed(oNewsfeed.get());
  }

  @Override
  public Newsfeed save(Newsfeed newsfeed) {
    hu.marko.szakdolgozat.spring.repository.model.Newsfeed entity = newsfeed.toEntity();
    entity.setId(null);
    Optional<Series> oSeries = seriesRepository.findById(entity.getSeries().getId());
    if (!oSeries.isPresent()) {
      throw new NotFoundException("Couldn't find news' series!");
    }
    entity.setSeries(oSeries.get());

    hu.marko.szakdolgozat.spring.repository.model.Newsfeed savedEntity = newsfeedRepository.save(entity);
    return new Newsfeed(savedEntity);
  }

  @Override
  public Boolean update(Long id, Newsfeed newsfeed) {
    Optional<hu.marko.szakdolgozat.spring.repository.model.Newsfeed> oNewsfeed = newsfeedRepository.findById(id);
    if (!oNewsfeed.isPresent()) {
      throw new NotFoundException("There is no newsfeed with such ID!");
    }
    hu.marko.szakdolgozat.spring.repository.model.Newsfeed entity = oNewsfeed.get();
    Optional<Series> oSeries = seriesRepository.findById(newsfeed.getSeries().getId());
    if (!oSeries.isPresent()) {
      throw new NotFoundException("Couldn't find news' series!");
    }

    if (newsfeed.getTitle() != null) {
      entity.setTitle(newsfeed.getTitle());
    }
    if (newsfeed.getDescription() != null) {
      entity.setDescription(newsfeed.getDescription());
    }
    if (entity.getSeries().getId() != oSeries.get().getId()) {
      entity.setSeries(oSeries.get());
    }

    hu.marko.szakdolgozat.spring.repository.model.Newsfeed savedEntity = newsfeedRepository.save(entity);
    if (savedEntity != null) {
      return true;
    }

    return false;
  }

  @Override
  public Long remove(Long id) {
    Optional<hu.marko.szakdolgozat.spring.repository.model.Newsfeed> oNewsfeed = newsfeedRepository.findById(id);
    if (!oNewsfeed.isPresent()) {
      throw new NotFoundException("There is no newsfeed with such ID!");
    }

    newsfeedRepository.deleteById(id);
    return id;
  }

  private String getOrder(String rawOrder) {
    switch (rawOrder) {
      case "series":
        return "s.title";
      default:
        return rawOrder;
    }
  }
}
