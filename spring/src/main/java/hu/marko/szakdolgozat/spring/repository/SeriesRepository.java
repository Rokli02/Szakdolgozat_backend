package hu.marko.szakdolgozat.spring.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import hu.marko.szakdolgozat.spring.repository.model.Series;

public interface SeriesRepository extends PagingAndSortingRepository<Series, Long> {
  @Query(value = "SELECT * FROM series s LEFT JOIN season e ON e.series_id = s.id LEFT JOIN series_categories_category scc ON s.id = scc.series_id LEFT JOIN category c ON scc.category_id = c.id LEFT JOIN image i ON i.id = s.image_id WHERE s.title LIKE %:filter% OR s.prod_year LIKE %:filter% OR c.name LIKE %:filter% GROUP BY s.id", nativeQuery = true)
  public Page<Series> findWithPagination(Pageable page, String filter);
}
