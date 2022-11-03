package hu.marko.szakdolgozat.spring.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import hu.marko.szakdolgozat.spring.repository.model.Series;

public interface SeriesRepository extends PagingAndSortingRepository<Series, Long> {
  @Query(value = "SELECT * FROM series s INNER JOIN season e ON e.id = s.season_id INNER JOIN series_categories_category scc ON s.id = scc.series_id INNER JOIN category c ON scc.category_id = c.id WHERE s.title LIKE %:filter% OR s.prodYear LIKE %:filter% OR c.name LIKE %:filter%", countQuery = "SELECT count(*) FROM series s INNER JOIN season e ON e.id = s.season_id INNER JOIN series_categories_category scc ON s.id = scc.series_id INNER JOIN category c ON scc.category_id = c.id WHERE s.title LIKE %:filter% OR s.prodYear LIKE %:filter% OR c.name LIKE %:filter%", nativeQuery = true)
  Page<Series> findWithPagination(Pageable page, String filter);
}
