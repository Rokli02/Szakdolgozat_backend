package hu.marko.szakdolgozat.spring.controller.model;

import java.sql.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Series {
  private Long id;
  private String title;
  @Min(1900)
  private Integer prodYear;
  @Min(1)
  private Integer ageLimit;
  @Min(1)
  private Integer length;
  private String added;
  private List<Season> seasons;
  private Set<Category> categories;
  // private Image image;

  public Series(hu.marko.szakdolgozat.spring.service.model.Series series) {
    List<Season> seasons = StreamSupport.stream(series.getSeasons().spliterator(), false).map(Season::new)
        .collect(Collectors.toList());
    Set<Category> categories = StreamSupport.stream(series.getCategories().spliterator(), false).map(Category::new)
        .collect(Collectors.toSet());

    this.id = series.getId();
    this.title = series.getTitle();
    this.prodYear = series.getProdYear();
    this.ageLimit = series.getAgeLimit();
    this.length = series.getLength();
    this.added = series.getAdded().toString();
    this.seasons = seasons;
    this.categories = categories;
  }

  public hu.marko.szakdolgozat.spring.service.model.Series toServiceSeries() {
    List<hu.marko.szakdolgozat.spring.service.model.Season> serviceSeasons = StreamSupport
        .stream(seasons.spliterator(), false).map((sn) -> sn.toServiceSeason()).collect(Collectors.toList());
    Set<hu.marko.szakdolgozat.spring.service.model.Category> serviceCategories = StreamSupport
        .stream(categories.spliterator(), false).map(Category::toServiceCategory).collect(Collectors.toSet());
    return new hu.marko.szakdolgozat.spring.service.model.Series(id, title, prodYear, ageLimit, length,
        new Date(java.util.Date.parse(added)),
        serviceSeasons, serviceCategories);
  }
}
