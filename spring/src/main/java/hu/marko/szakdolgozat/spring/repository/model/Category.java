package hu.marko.szakdolgozat.spring.repository.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Category {
  @Id
  @GeneratedValue
  Long id;
  @Column(nullable = false)
  String name;
  // Series[] serieses;
}
