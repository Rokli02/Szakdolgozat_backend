package hu.marko.szakdolgozat.spring.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import hu.marko.szakdolgozat.spring.repository.model.Series;
import hu.marko.szakdolgozat.spring.repository.model.User;
import hu.marko.szakdolgozat.spring.repository.model.Userseries;

public interface UserseriesRepository extends PagingAndSortingRepository<Userseries, Long> {
  @Query(value = "SELECT * FROM userseries us INNER JOIN series s ON us.series_id = s.id INNER JOIN series_categories_category scc ON scc.series_id = s.id INNER JOIN category c ON scc.category_id = c.id INNER JOIN status st ON us.status_id = st.id INNER JOIN season se ON se.series_id = s.id WHERE user_id = :userId AND s.title LIKE %:filter% GROUP BY us.id", countQuery = "SELECT count(*) FROM userseries us INNER JOIN series s ON us.series_id = s.id INNER JOIN series_categories_category scc ON scc.series_id = s.id INNER JOIN category c ON scc.category_id = c.id INNER JOIN status st ON us.status_id = st.id INNER JOIN season se ON se.series_id = s.id WHERE user_id = :userId", nativeQuery = true)
  Page<Userseries> findWithPagination(Pageable page, String filter, Long userId);

  @Query(value = "SELECT * FROM userseries us INNER JOIN series s ON us.series_id = s.id INNER JOIN series_categories_category scc ON scc.series_id = s.id INNER JOIN category c ON scc.category_id = c.id INNER JOIN status st ON us.status_id = st.id INNER JOIN season se ON se.series_id = s.id WHERE status_id = :statusId AND user_id = :userId AND s.title LIKE %:filter% GROUP BY us.id", countQuery = "SELECT count(*) FROM userseries us INNER JOIN series s ON us.series_id = s.id INNER JOIN series_categories_category scc ON scc.series_id = s.id INNER JOIN category c ON scc.category_id = c.id INNER JOIN status st ON us.status_id = st.id INNER JOIN season se ON se.series_id = s.id WHERE user_id = :userId", nativeQuery = true)
  Page<Userseries> findWithPaginationAndStatus(Pageable page, String filter, Long userId, Long statusId);

  Optional<Userseries> findByUserAndSeries(User user, Series series);
}
