package hu.marko.szakdolgozat.spring.controller.model.newModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import hu.marko.szakdolgozat.spring.controller.model.Category;
import hu.marko.szakdolgozat.spring.controller.model.Image;
import hu.marko.szakdolgozat.spring.controller.model.Season;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NewSeries {
  @NotBlank
  private String title;
  @NotNull
  @Min(1900)
  private Integer prodYear;
  @NotNull
  @Min(1)
  private Integer ageLimit;
  @NotNull
  @Min(1)
  private Integer length;
  private List<Season> seasons;
  private Set<Category> categories;
  private Image image;

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

    return new hu.marko.szakdolgozat.spring.service.model.Series(null, title, prodYear, ageLimit, length, null,
        serviceSeasons, serviceCategories, image != null ? image.toServiceImage() : null);
  }
}
