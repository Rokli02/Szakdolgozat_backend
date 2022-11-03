package hu.marko.szakdolgozat.spring.repository.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class Category {
  @Id
  @GeneratedValue
  Long id;
  @Column(nullable = false)
  String name;
  @ManyToMany(mappedBy = "categories")
  Set<Series> serieses;

  public Category(Long id, String name) {
    this.id = id;
    this.name = name;
  }
}
