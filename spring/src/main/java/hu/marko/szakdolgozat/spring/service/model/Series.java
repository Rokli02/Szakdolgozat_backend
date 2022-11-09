package hu.marko.szakdolgozat.spring.service.model;

import java.util.ArrayList;
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
  private String added;
  private List<Season> seasons;
  private Set<Category> categories;
  private Image image;

  public Series(hu.marko.szakdolgozat.spring.repository.model.Series series) {
    List<Season> ss;
    if (series.getSeasons() != null) {
      ss = StreamSupport.stream(series.getSeasons().spliterator(), false)
          .map(Season::new).collect(Collectors.toList());
    } else {
      ss = new ArrayList<Season>();
    }
    Set<Category> cs;
    if (series.getCategories() != null) {
      cs = StreamSupport.stream(series.getCategories().spliterator(), false).map(Category::new)
          .collect(Collectors.toSet());
    } else {
      cs = new HashSet<Category>();
    }
    this.id = series.getId();
    this.title = series.getTitle();
    this.prodYear = series.getProdYear();
    this.ageLimit = series.getAgeLimit();
    this.length = series.getLength();
    this.added = series.getAdded() != null ? series.getAdded().toString().split(" ")[0] : null;
    this.seasons = ss;
    this.categories = cs;
    this.image = series.getImage() != null ? new Image(series.getImage()) : null;
  }

  public hu.marko.szakdolgozat.spring.repository.model.Series toEntity() {
    List<hu.marko.szakdolgozat.spring.repository.model.Season> seasonList;
    if (seasons != null) {
      seasonList = StreamSupport.stream(seasons.spliterator(), false)
          .map((season) -> new hu.marko.szakdolgozat.spring.repository.model.Season(
              season.getId(),
              season.getSeason(), season.getEpisode(), null))
          .collect(Collectors.toList());
    } else {
      seasonList = new ArrayList<hu.marko.szakdolgozat.spring.repository.model.Season>();
    }
    Set<hu.marko.szakdolgozat.spring.repository.model.Category> categorySet;
    if (categories != null) {
      categorySet = StreamSupport.stream(categories.spliterator(), false)
          .map(cat -> new hu.marko.szakdolgozat.spring.repository.model.Category(
              cat.getId(),
              cat.getName()))
          .collect(Collectors.toSet());
    } else {
      categorySet = new HashSet<hu.marko.szakdolgozat.spring.repository.model.Category>();
    }

    return new hu.marko.szakdolgozat.spring.repository.model.Series(
        id, title, prodYear, ageLimit, length, null,
        seasonList, categorySet, null, null,
        image != null ? image.toEntity() : null);
  }
}
