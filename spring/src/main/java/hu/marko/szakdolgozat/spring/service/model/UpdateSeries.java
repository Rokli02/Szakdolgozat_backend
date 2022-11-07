package hu.marko.szakdolgozat.spring.service.model;

import java.sql.Date;
import java.util.List;
import java.util.Set;

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
  private Image image;
}
