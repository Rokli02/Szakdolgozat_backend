package hu.marko.szakdolgozat.spring.controller;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.marko.szakdolgozat.spring.controller.model.newModel.NewStatus;
import hu.marko.szakdolgozat.spring.controller.model.Status;

@RestController
@RequestMapping("/api/statuses")
public class StatusController {

  @GetMapping("/")
  public List<Status> getAllStatuses() {
    return null;
  }

  @PostMapping("/")
  public Status saveStatus(@RequestBody @NotNull NewStatus newStatus) {
    return null;
  }

  @PutMapping("/:id")
  public String updateStatus(@PathVariable("id") @NotNull Integer id, @RequestBody @NotNull NewStatus newStatus) {
    return "updateStatus";
  }
}
