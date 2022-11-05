package hu.marko.szakdolgozat.spring.repository.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Image {
  @Id
  @GeneratedValue
  private Long id;
  @Column(nullable = false)
  private String name;
  @Column(columnDefinition = "varchar(255) NOT NULL default '0px'")
  private String x_offset;
  @Column(columnDefinition = "varchar(255) NOT NULL default '0px'")
  private String y_offset;
  @OneToOne(optional = false)
  @JoinColumn(name = "id", nullable = true)
  private Series series;
}
