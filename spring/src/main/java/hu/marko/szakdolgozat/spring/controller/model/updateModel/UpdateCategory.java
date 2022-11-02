package hu.marko.szakdolgozat.spring.controller.model.updateModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateCategory {
  private Long id;
  private boolean remove;
}
