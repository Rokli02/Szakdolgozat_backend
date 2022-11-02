package hu.marko.szakdolgozat.spring.controller;

import java.util.List;

import javax.validation.constraints.NotNull;

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

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

  @GetMapping("")
  public ResponseEntity<List<Category>> getAllCategory() {
    return null;
  }

  @PostMapping("")
  public ResponseEntity<Category> saveCategory(@RequestBody @NotNull NewCategory newCategory) {
    return null;
  }

  @PutMapping("/{id}")
  public ResponseEntity<Message> updateCategory(@PathVariable("id") @NotNull Integer id,
      @RequestBody @NotNull Category category) {
    return null;
  }
}
