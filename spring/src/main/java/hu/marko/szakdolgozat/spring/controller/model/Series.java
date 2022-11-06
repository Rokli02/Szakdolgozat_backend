package hu.marko.szakdolgozat.spring.controller.model;

import java.util.ArrayList;
import java.util.HashSet;
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
  private Image image;

  public Series(hu.marko.szakdolgozat.spring.service.model.Series series) {
    List<Season> seasonsList;
    if (series.getSeasons() != null) {
      seasonsList = StreamSupport.stream(series.getSeasons().spliterator(), false).map(Season::new)
          .collect(Collectors.toList());
    } else {
      seasonsList = new ArrayList<Season>();
    }
    Set<Category> categories;
    if (series.getCategories() != null) {
      categories = StreamSupport.stream(series.getCategories().spliterator(), false).map(Category::new)
          .collect(Collectors.toSet());
    } else {
      categories = new HashSet<Category>();
    }

    this.id = series.getId();
    this.title = series.getTitle();
    this.prodYear = series.getProdYear();
    this.ageLimit = series.getAgeLimit();
    this.length = series.getLength();
    this.added = series.getAdded() != null ? series.getAdded().toString() : null;
    this.seasons = seasonsList;
    this.categories = categories;
    this.image = series.getImage() != null ? new Image(series.getImage()) : null;
  }

  public hu.marko.szakdolgozat.spring.service.model.Series toServiceSeries() {
    List<hu.marko.szakdolgozat.spring.service.model.Season> serviceSeasons;
    if (this.seasons != null) {
      serviceSeasons = StreamSupport.stream(seasons.spliterator(), false)
          .map((sn) -> sn.toServiceSeason()).collect(Collectors.toList());
    } else {
      serviceSeasons = new ArrayList<hu.marko.szakdolgozat.spring.service.model.Season>();
    }

    Set<hu.marko.szakdolgozat.spring.service.model.Category> serviceCategories;
    if (this.categories != null) {
      serviceCategories = StreamSupport.stream(categories.spliterator(), false)
          .map(Category::toServiceCategory).collect(Collectors.toSet());
    } else {
      serviceCategories = new HashSet<hu.marko.szakdolgozat.spring.service.model.Category>();
    }

    return new hu.marko.szakdolgozat.spring.service.model.Series(id, title, prodYear, ageLimit, length,
        null, serviceSeasons, serviceCategories,
        image != null ? image.toServiceImage() : null);
  }
}
