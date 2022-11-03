package hu.marko.szakdolgozat.spring.service.model;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Series {
  private Long id;
  private String title;
  private Integer prodYear;
  private Integer ageLimit;
  private Integer length;
  private Date added;
  private List<Season> seasons;
  private Set<Category> categories;
  // private Image image;

  public Series(hu.marko.szakdolgozat.spring.repository.model.Series series) {
    List<Season> ss = StreamSupport.stream(series.getSeasons().spliterator(), false).map(Season::new)
        .collect(Collectors.toList());
    Set<Category> cs = StreamSupport.stream(series.getCategories().spliterator(), false).map(Category::new)
        .collect(Collectors.toSet());
    this.id = series.getId();
    this.title = series.getTitle();
    this.prodYear = series.getProdYear();
    this.ageLimit = series.getAgeLimit();
    this.length = series.getLength();
    this.added = series.getAdded();
    this.seasons = ss;
    this.categories = cs;
  }

  public hu.marko.szakdolgozat.spring.repository.model.Series toEntity() {
    return new hu.marko.szakdolgozat.spring.repository.model.Series(id, title, prodYear, ageLimit, length, added,
        StreamSupport.stream(seasons.spliterator(), false)
            .map((season) -> new hu.marko.szakdolgozat.spring.repository.model.Season(season, this))
            .collect(Collectors.toList()),
        new HashSet<hu.marko.szakdolgozat.spring.repository.model.Category>(), null);
  }
}
