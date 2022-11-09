package hu.marko.szakdolgozat.spring.repository.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;

import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "f_user_id", "f_series_id" }))
public class Userseries {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(columnDefinition = "int(11)")
  private Long id;
  @ManyToOne(targetEntity = User.class)
  @JoinColumn(name = "f_user_id", nullable = false)
  private User user;
  @ManyToOne(targetEntity = Series.class)
  @JoinColumn(name = "f_series_id", nullable = false)
  private Series series;
  @ManyToOne(targetEntity = Status.class)
  @JoinColumn(name = "f_status_id", nullable = false)
  private Status status;
  @Min(1)
  @Column(columnDefinition = "int(11) default 1")
  private Integer season;
  @Min(1)
  @Column(columnDefinition = "int(11) default 1")
  private Integer episode;
  @UpdateTimestamp
  @Column(columnDefinition = "datetime(6) default CURRENT_TIMESTAMP(6)")
  private Date modification;
}
