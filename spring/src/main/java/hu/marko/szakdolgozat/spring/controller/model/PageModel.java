package hu.marko.szakdolgozat.spring.controller.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PageModel<T> {
  private List<T> models;
  private int count;
}
