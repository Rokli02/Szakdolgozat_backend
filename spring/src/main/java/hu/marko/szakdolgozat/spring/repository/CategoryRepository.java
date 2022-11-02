package hu.marko.szakdolgozat.spring.repository;

import org.springframework.data.repository.CrudRepository;

import hu.marko.szakdolgozat.spring.repository.model.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {

}
