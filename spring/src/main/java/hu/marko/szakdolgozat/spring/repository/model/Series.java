package hu.marko.szakdolgozat.spring.repository.model;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Series {
  @Id
  @GeneratedValue
  private Long id;
  @Column(nullable = false)
  private String title;
  @Column(nullable = false)
  @Min(1900)
  private Integer prodYear;
  @Column(nullable = false)
  @Min(1)
  private Integer ageLimit;
  @Column(nullable = false)
  @Min(1)
  private Integer length;
  @CreationTimestamp
  @Column(updatable = false, columnDefinition = "datetime(6) default CURRENT_TIMESTAMP(6)")
  private Date added;
  @OneToMany(mappedBy = "series", targetEntity = Season.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private List<Season> seasons;
  @ManyToMany
  @JoinTable(name = "series_categories_category", joinColumns = @JoinColumn(name = "series_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
  private Set<Category> categories;
  @OneToMany(mappedBy = "series", targetEntity = Newsfeed.class, fetch = FetchType.LAZY)
  private List<Newsfeed> newsfeeds;
  // private List<UserSeries> userserieses;
  // private Image image;
}
