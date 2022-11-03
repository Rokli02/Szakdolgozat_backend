package hu.marko.szakdolgozat.spring.controller.model.updateModel;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import hu.marko.szakdolgozat.spring.controller.model.Season;
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
  private String added;
  private List<Season> seasons;
  private Set<UpdateCategory> categories;
  // private Image image;

  public hu.marko.szakdolgozat.spring.service.model.UpdateSeries toServiceSeries() {
    List<hu.marko.szakdolgozat.spring.service.model.Season> serviceSeasons = StreamSupport
        .stream(seasons.spliterator(), false).map((sn) -> sn.toServiceSeason()).collect(Collectors.toList());
    Set<hu.marko.szakdolgozat.spring.service.model.UpdateCategory> serviceCategories = StreamSupport
        .stream(categories.spliterator(), false).map(UpdateCategory::toServiceUpdateCategory)
        .collect(Collectors.toSet());

    return new hu.marko.szakdolgozat.spring.service.model.UpdateSeries(id, title, prodYear, ageLimit, length, null,
        serviceSeasons, serviceCategories);
  }
}
