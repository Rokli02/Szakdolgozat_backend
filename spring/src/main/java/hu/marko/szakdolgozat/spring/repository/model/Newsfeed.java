package hu.marko.szakdolgozat.spring.repository.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(columnDefinition = "int(11)")
  private Long id;
  @Column(nullable = false)
  private String title;
  @Column(columnDefinition = "text")
  private String description;
  @UpdateTimestamp
  @Column(columnDefinition = "datetime(6) default CURRENT_TIMESTAMP(6)")
  private Timestamp modification;
  @ManyToOne(targetEntity = Series.class)
  @JoinColumn(name = "f_series_id", nullable = false)
  private Series series;
}
