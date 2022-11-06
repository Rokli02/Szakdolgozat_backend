package hu.marko.szakdolgozat.spring.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import hu.marko.szakdolgozat.spring.repository.model.Newsfeed;

public interface NewsfeedRepository extends PagingAndSortingRepository<Newsfeed, Long> {
  @Query(value = "SELECT * FROM newsfeed n LEFT JOIN series s ON n.f_series_id = s.id WHERE s.title LIKE %:filter% OR n.title LIKE %:filter% OR n.modification LIKE %:filter% GROUP BY n.id", countQuery = "SELECT count(*) FROM newsfeed n LEFT JOIN series s ON n.f_series_id = s.id WHERE s.title LIKE %:filter% OR n.title LIKE %:filter% OR n.modification LIKE %:filter% GROUP BY n.id", nativeQuery = true)
  Page<Newsfeed> findWithPagination(Pageable page, String filter);

  @Query(value = "SELECT * FROM newsfeed n LEFT JOIN series s ON n.f_series_id = s.id JOIN userseries us ON s.id = us.f_series_id WHERE us.f_user_id = :userId AND (s.title LIKE %:filter% OR n.title LIKE %:filter% OR n.modification LIKE %:filter%) GROUP BY n.id", countQuery = "SELECT count(*) FROM newsfeed n LEFT JOIN series s ON n.f_series_id = s.id JOIN userseries us ON s.id = us.f_series_id WHERE us.f_user_id = :userId AND (s.title LIKE %:filter% OR n.title LIKE %:filter% OR n.modification LIKE %:filter%) GROUP BY n.id", nativeQuery = true)
  Page<Newsfeed> findPersonalWithPagination(Pageable page, String filter, Long userId);
}
