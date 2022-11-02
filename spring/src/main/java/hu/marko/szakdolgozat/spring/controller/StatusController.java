package hu.marko.szakdolgozat.spring.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.marko.szakdolgozat.spring.controller.model.newModel.NewStatus;
import hu.marko.szakdolgozat.spring.controller.model.Message;
import hu.marko.szakdolgozat.spring.controller.model.Status;

@RestController
@RequestMapping("/api/statuses")
public class StatusController {

  @GetMapping("")
  public ResponseEntity<List<Status>> getAllStatuses() { //
    return null;
  }

  @PostMapping("")
  public ResponseEntity<Status> saveStatus(@RequestBody @NotNull NewStatus newStatus) {
    return null;
  }

  @PutMapping("/{id}")
  public ResponseEntity<Message> updateStatus(@PathVariable("id") @NotNull Integer id,
      @RequestBody @NotNull NewStatus newStatus, HttpServletRequest request) {
    return ResponseEntity.ok().body(new Message(request.getAttribute("userId").toString()));
  }
}
