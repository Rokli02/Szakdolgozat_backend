package hu.marko.szakdolgozat.spring.service;

import java.util.List;

import hu.marko.szakdolgozat.spring.service.model.Category;

public interface CategoryService {
  List<Category> findAll();

  Category save(Category category);

  boolean update(int id, Category category);
}
