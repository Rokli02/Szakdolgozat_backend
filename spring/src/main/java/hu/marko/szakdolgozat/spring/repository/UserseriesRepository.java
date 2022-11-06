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
  @Query(value = "SELECT * FROM userseries us LEFT JOIN series s ON us.f_series_id = s.id LEFT JOIN series_categories_category scc ON scc.f_series_id = s.id LEFT JOIN category c ON scc.f_category_id = c.id LEFT JOIN status st ON us.f_status_id = st.id LEFT JOIN season se ON se.f_series_id = s.id WHERE f_user_id = :userId AND (s.title LIKE %:filter% OR s.prod_year LIKE %:filter% OR c.name LIKE %:filter%) GROUP BY us.id", countQuery = "SELECT count(*) FROM userseries us LEFT JOIN series s ON us.f_series_id = s.id LEFT JOIN series_categories_category scc ON scc.f_series_id = s.id LEFT JOIN category c ON scc.f_category_id = c.id LEFT JOIN status st ON us.f_status_id = st.id LEFT JOIN season se ON se.f_series_id = s.id WHERE f_user_id = :userId AND (s.title LIKE %:filter% OR s.prod_year LIKE %:filter% OR c.name LIKE %:filter%) GROUP BY us.id", nativeQuery = true)
  Page<Userseries> findWithPagination(Pageable page, String filter, Long userId);

  @Query(value = "SELECT * FROM userseries us LEFT JOIN series s ON us.f_series_id = s.id LEFT JOIN series_categories_category scc ON scc.f_series_id = s.id LEFT JOIN category c ON scc.f_category_id = c.id LEFT JOIN status st ON us.f_status_id = st.id LEFT JOIN season se ON se.f_series_id = s.id WHERE f_status_id = :statusId AND f_user_id = :userId AND (s.title LIKE %:filter% OR s.prod_year LIKE %:filter% OR c.name LIKE %:filter%) GROUP BY us.id", countQuery = "SELECT count(*) FROM userseries us LEFT JOIN series s ON us.f_series_id = s.id LEFT JOIN series_categories_category scc ON scc.f_series_id = s.id LEFT JOIN category c ON scc.f_category_id = c.id LEFT JOIN status st ON us.f_status_id = st.id LEFT JOIN season se ON se.f_series_id = s.id WHERE f_status_id = :statusId AND f_user_id = :userId AND (s.title LIKE %:filter% OR s.prod_year LIKE %:filter% OR c.name LIKE %:filter%) GROUP BY us.id", nativeQuery = true)
  Page<Userseries> findWithPaginationAndStatus(Pageable page, String filter, Long userId, Long statusId);

  Optional<Userseries> findByUserAndSeries(User user, Series series);
}
