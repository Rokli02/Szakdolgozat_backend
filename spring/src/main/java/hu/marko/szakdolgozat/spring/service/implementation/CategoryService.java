package hu.marko.szakdolgozat.spring.service.implementation;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import hu.marko.szakdolgozat.spring.exception.BadRequestException;
import hu.marko.szakdolgozat.spring.repository.CategoryRepository;
import hu.marko.szakdolgozat.spring.service.model.Category;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CategoryService implements hu.marko.szakdolgozat.spring.service.CategoryService {
  private final CategoryRepository categoryRepository;

  @Override
  public List<Category> findAll() {
    return StreamSupport.stream(categoryRepository.findAll().spliterator(), false)
        .map(Category::new).collect(Collectors.toList());
  }

  @Override
  public Category save(Category category) {
    category.setId(null);
    hu.marko.szakdolgozat.spring.repository.model.Category entity = categoryRepository.save(category.toEntity());
    if (entity == null) {
      throw new BadRequestException("Something went wrong during category save!");
    }

    return new Category(entity);
  }

  @Override
  public Boolean update(Long id, Category category) {
    Optional<hu.marko.szakdolgozat.spring.repository.model.Category> oEntity = categoryRepository.findById(id);
    if (!oEntity.isPresent()) {
      throw new BadRequestException("Category with such id doesn't exists!!");
    }
    hu.marko.szakdolgozat.spring.repository.model.Category entity = oEntity.get();
    entity.setId(id);
    if (category.getName() != null) {
      entity.setName(category.getName());
    }
    hu.marko.szakdolgozat.spring.repository.model.Category savedEntity = categoryRepository.save(entity);
    if (savedEntity == null) {
      throw new BadRequestException("Something went wrong during category update!");
    }

    return true;
  }

}
