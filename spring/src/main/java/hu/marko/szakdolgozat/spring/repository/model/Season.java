package hu.marko.szakdolgozat.spring.repository.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "season", "series_id" }))
public class Season {
  @Id
  @GeneratedValue
  private Long id;
  @Column(nullable = false)
  @Min(1)
  private Integer season;
  @Column(nullable = false)
  @Min(1)
  private Integer episode;
  @ManyToOne(targetEntity = Series.class)
  @JoinColumn(name = "series_id", nullable = false)
  private Series series;

  public Season(hu.marko.szakdolgozat.spring.service.model.Season season,
      hu.marko.szakdolgozat.spring.service.model.Series series) {
    this(season.getId(), season.getSeason(), season.getEpisode(), series.toEntity());
  }
}
