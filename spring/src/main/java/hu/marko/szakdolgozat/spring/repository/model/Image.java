package hu.marko.szakdolgozat.spring.repository.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(columnDefinition = "int(11)")
  private Long id;
  @Column(nullable = false, unique = true)
  private String name;
  @Column(columnDefinition = "varchar(255) default '0px'")
  private String x_offset = "0px";
  @Column(columnDefinition = "varchar(255) default '0px'")
  private String y_offset = "0px";
  @OneToOne(optional = false, mappedBy = "image")
  private Series series;
}
