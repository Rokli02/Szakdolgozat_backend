package hu.marko.szakdolgozat.spring.service.model;

import java.sql.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateSeries {
  private Long id;
  private String title;
  private Integer prodYear;
  private Integer ageLimit;
  private Integer length;
  private Date added;
  private List<Season> seasons;
  private Set<UpdateCategory> categories;
  // private Image image;

  public UpdateSeries(hu.marko.szakdolgozat.spring.repository.model.Series series) {
    List<Season> ss = StreamSupport.stream(series.getSeasons().spliterator(), false).map(Season::new)
        .collect(Collectors.toList());
    Set<UpdateCategory> cs = StreamSupport.stream(series.getCategories().spliterator(), false).map(UpdateCategory::new)
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
}
