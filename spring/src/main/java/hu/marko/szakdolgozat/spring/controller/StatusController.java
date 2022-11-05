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

import hu.marko.szakdolgozat.spring.controller.model.newModel.NewStatus;
import hu.marko.szakdolgozat.spring.controller.model.wrapper.StatusWrapper;
import hu.marko.szakdolgozat.spring.controller.model.wrapper.StatusesWrapper;
import hu.marko.szakdolgozat.spring.service.StatusService;
import lombok.AllArgsConstructor;
import hu.marko.szakdolgozat.spring.controller.model.Message;
import hu.marko.szakdolgozat.spring.controller.model.Status;

@AllArgsConstructor
@RestController
@RequestMapping("/api/statuses")
public class StatusController {
  private final StatusService statusService;

  @GetMapping("")
  public ResponseEntity<StatusesWrapper> getAllStatuses() { //
    return ResponseEntity.ok().body(new StatusesWrapper(StreamSupport
        .stream(statusService.findAll().spliterator(), false)
        .map(Status::new).collect(Collectors.toList())));
  }

  @PostMapping("")
  public ResponseEntity<StatusWrapper> saveStatus(@RequestBody @NotNull NewStatus newStatus) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new StatusWrapper(new Status(statusService.save(newStatus.toServiceStatus()))));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Message> updateStatus(@PathVariable("id") @NotNull Long id,
      @RequestBody @NotNull Status status) {
    System.out.println(status.toString());
    if (statusService.update(id, status.toServiceStatus())) {
      return ResponseEntity.ok().body(new Message("Status is updated succesfully!"));
    }
    return ResponseEntity.badRequest().body(new Message("Something went wrong during update!"));
  }
}
