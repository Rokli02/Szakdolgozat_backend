package hu.marko.szakdolgozat.spring.controller.model.wrapper;

import java.util.List;

import hu.marko.szakdolgozat.spring.controller.model.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StatusesWrapper {
  private List<Status> statuses;
}
