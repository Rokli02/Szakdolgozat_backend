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
import hu.marko.szakdolgozat.spring.repository.SeriesRepository;
import hu.marko.szakdolgozat.spring.repository.StatusRepository;
import hu.marko.szakdolgozat.spring.repository.UserRepository;
import hu.marko.szakdolgozat.spring.repository.UserseriesRepository;
import hu.marko.szakdolgozat.spring.repository.model.Series;
import hu.marko.szakdolgozat.spring.repository.model.Status;
import hu.marko.szakdolgozat.spring.repository.model.User;
import hu.marko.szakdolgozat.spring.repository.model.Userseries;
import hu.marko.szakdolgozat.spring.service.UserSeriesService;
import hu.marko.szakdolgozat.spring.service.model.PageModel;
import hu.marko.szakdolgozat.spring.service.model.UserSeries;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserseriesService implements UserSeriesService {
  private UserseriesRepository userseriesRepository;
  private UserRepository userRepository;
  private SeriesRepository seriesRepository;
  private StatusRepository statusRepository;
  private final Long DEFAULT_STATUS_ID = 1L;

  @Override
  public PageModel<UserSeries> findByPageAndSizeAndFilterAndStatusAndOrder(Long userId, Integer page, Integer size,
      String filter, Long status, String order, Boolean ascendingDirection) {
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

    String interOrder = "s.id";
    if (order != null) {
      interOrder = getOrder(order);
    }

    System.out.println(
        String.format("\n\nDatas in service:\nUserId: %d\nPage: %d\nSize: %d\nFilter: %s\nStatus: %d\nOrder: %s\n",
            userId, page, size, interFilter, status, interOrder));
    Page<Userseries> pagedEntity;
    if (status != null) {
      Optional<Status> oStatus = statusRepository.findById(status);
      if (!oStatus.isPresent()) {
        throw new BadRequestException("There is no such status!");
      }

      pagedEntity = userseriesRepository
          .findWithPaginationAndStatus(PageRequest.of(page - 1, size, Sort.by(direction, interOrder)), interFilter,
              userId, status);
    } else {
      pagedEntity = userseriesRepository
          .findWithPagination(PageRequest.of(page - 1, size, Sort.by(direction, interOrder)), interFilter, userId);
    }
    pagedEntity.getContent().stream().forEach(
        us -> System.out.println(String.format("UserSeries: { SeriesId: %d, Title: %s, season: %d, episode: %d}",
            us.getSeries().getId(), us.getSeries().getTitle(), us.getSeason(), us.getEpisode())));
    List<UserSeries> seriesList = StreamSupport.stream(pagedEntity.getContent().spliterator(), false)
        .map(UserSeries::new)
        .collect(Collectors.toList());
    PageModel<UserSeries> pageModel = new PageModel<>(seriesList, pagedEntity.getTotalElements());
    return pageModel;
  }

  @Override
  public UserSeries findOne(Long userId, Long seriesId) {
    Optional<User> oUser = userRepository.findById(userId);
    Optional<Series> oSeries = seriesRepository.findById(seriesId);
    if (!oUser.isPresent() || !oSeries.isPresent()) {
      throw new NotFoundException("Couldn't find user or series!");
    }

    Optional<Userseries> oUserseries = userseriesRepository.findByUserAndSeries(oUser.get(), oSeries.get());
    if (!oUserseries.isPresent()) {
      throw new NotFoundException("There is no such userseries!");
    }

    return new UserSeries(oUserseries.get());
  }

  @Override
  public UserSeries save(Long userId, UserSeries userSeries) {
    Optional<User> oUser = userRepository.findById(userId);
    Optional<Series> oSeries = seriesRepository.findById(userSeries.getSeries().getId());
    if (!oUser.isPresent() || !oSeries.isPresent()) {
      throw new NotFoundException("Couldn't find user or series!");
    }

    Optional<Userseries> oUserseries = userseriesRepository.findByUserAndSeries(oUser.get(), oSeries.get());
    if (oUserseries.isPresent()) {
      throw new NotFoundException("This userseries is already saved!");
    }

    Userseries userSeriesEntity = userSeries.toEntity();
    userSeriesEntity.setUser(oUser.get());
    if (userSeriesEntity.getStatus() == null) {
      Optional<Status> oStatus = statusRepository.findById(DEFAULT_STATUS_ID);
      if (!oStatus.isPresent()) {
        throw new RuntimeException("Backend -default status- error!");
      }
      userSeriesEntity.setStatus(oStatus.get());
    }

    Userseries savedUserseries = userseriesRepository.save(userSeriesEntity);
    return new UserSeries(savedUserseries);
  }

  @Override
  public Boolean update(Long userId, Long seriesId, UserSeries userSeries) {
    Optional<User> oUser = userRepository.findById(userId);
    Optional<Series> oSeries = seriesRepository.findById(seriesId);
    if (!oUser.isPresent() || !oSeries.isPresent()) {
      throw new NotFoundException("Couldn't find user or series!");
    }

    Optional<Userseries> oUserseries = userseriesRepository.findByUserAndSeries(oUser.get(), oSeries.get());
    if (!oUserseries.isPresent()) {
      throw new NotFoundException("There is no userseries with such ");
    }
    Userseries entity = oUserseries.get();
    entity.setUser(oUser.get());
    entity.setSeries(oSeries.get());
    if (userSeries.getSeason() != null) {
      entity.setSeason(userSeries.getSeason());
    }
    if (userSeries.getEpisode() != null) {
      entity.setEpisode(userSeries.getEpisode());
    }
    if (userSeries.getStatus() != null) {
      Optional<Status> oStatus = statusRepository.findById(userSeries.getStatus().getId());
      if (!oStatus.isPresent()) {
        throw new BadRequestException("There is no such status!");
      }
      entity.setStatus(oStatus.get());
    }

    Userseries updatedUserseries = userseriesRepository.save(entity);
    if (updatedUserseries != null) {
      return true;
    }

    return false;
  }

  @Override
  public Long remove(Long userId, Long seriesId) {
    Optional<User> oUser = userRepository.findById(userId);
    Optional<Series> oSeries = seriesRepository.findById(seriesId);
    if (!oUser.isPresent() || !oSeries.isPresent()) {
      throw new NotFoundException("Couldn't find user or series!");
    }

    Optional<Userseries> oUserseries = userseriesRepository.findByUserAndSeries(oUser.get(), oSeries.get());
    if (!oUserseries.isPresent()) {
      throw new NotFoundException("There is no such userseries!");
    }

    userseriesRepository.delete(oUserseries.get());
    return oUserseries.get().getId();
  }

  private String getOrder(String rawOrder) {
    switch (rawOrder) {
      case "title":
        return "s.title";
      case "age_limit":
        return "s.age_limit";
      case "length":
        return "s.length";
      case "modification":
        return "modification";
      case "prod_year":
        return "s.prod_year";
      default:
        return "s.id";
    }
  }
}
