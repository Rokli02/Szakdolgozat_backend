package hu.marko.szakdolgozat.spring.repository.model;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Min;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Series {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private String title;
  @Min(1900)
  @Column(nullable = false, columnDefinition = "year(4)")
  private Integer prodYear;
  @Min(1)
  @Column(nullable = false)
  private Integer ageLimit;
  @Min(1)
  @Column(nullable = false)
  private Integer length;
  @CreationTimestamp
  @Column(updatable = false, columnDefinition = "datetime(6) default CURRENT_TIMESTAMP(6)")
  private Date added;
  @OneToMany(mappedBy = "series", targetEntity = Season.class, cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Season> seasons;
  @ManyToMany
  @JoinTable(name = "series_categories_category", joinColumns = @JoinColumn(name = "f_series_id"), inverseJoinColumns = @JoinColumn(name = "f_category_id"))
  private Set<Category> categories;
  @OneToMany(mappedBy = "series", targetEntity = Newsfeed.class)
  @JsonIgnore
  private List<Newsfeed> newsfeeds;
  @OneToMany(mappedBy = "series", targetEntity = Userseries.class)
  private List<Userseries> userserieses;
  @OneToOne(cascade = CascadeType.ALL, optional = true, orphanRemoval = true)
  @JoinColumn(name = "f_image_id", unique = true, nullable = true)
  private Image image;
}
