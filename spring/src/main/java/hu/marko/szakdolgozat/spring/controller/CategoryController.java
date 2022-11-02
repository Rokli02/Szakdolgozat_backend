package hu.marko.szakdolgozat.spring.controller;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.marko.szakdolgozat.spring.controller.model.Category;
import hu.marko.szakdolgozat.spring.controller.model.Message;
import hu.marko.szakdolgozat.spring.controller.model.newModel.NewCategory;
import hu.marko.szakdolgozat.spring.controller.model.wrapper.CategoriesWrapper;
import hu.marko.szakdolgozat.spring.controller.model.wrapper.CategoryWrapper;
import hu.marko.szakdolgozat.spring.service.CategoryService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {
  private final CategoryService categoryService;

  @GetMapping("")
  public ResponseEntity<CategoriesWrapper> getAllCategory() {
    return ResponseEntity.ok().body(new CategoriesWrapper(StreamSupport
        .stream(categoryService.findAll().spliterator(), false)
        .map(Category::new).collect(Collectors.toList())));
  }

  @PostMapping("")
  public ResponseEntity<CategoryWrapper> saveCategory(@RequestBody @NotNull NewCategory newCategory) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new CategoryWrapper(new Category(categoryService.save(newCategory.toServiceCategory()))));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Message> updateCategory(@PathVariable("id") @NotNull Long id,
      @RequestBody @NotNull Category category) {
    if (categoryService.update(id, category.toServiceCategory())) {
      return ResponseEntity.ok().body(new Message("Category is updated succesfully!"));
    }
    return ResponseEntity.badRequest().body(new Message("Something went wrong during update!"));
  }
}
