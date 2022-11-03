package hu.marko.szakdolgozat.spring.repository.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Newsfeed {
  @Id
  @GeneratedValue
  private Long id;
  @Column(nullable = false)
  private String title;
  private String description;
  @UpdateTimestamp
  @Column(columnDefinition = "datetime(6) default CURRENT_TIMESTAMP(6)")
  private Date modification;
  @ManyToOne(targetEntity = Series.class)
  @JoinColumn(name = "series_id", nullable = false)
  private Series series;
}
