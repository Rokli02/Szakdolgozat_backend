package hu.marko.szakdolgozat.spring.controller;

import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hu.marko.szakdolgozat.spring.controller.model.Message;
import hu.marko.szakdolgozat.spring.controller.model.PageModel;
import hu.marko.szakdolgozat.spring.controller.model.User;

@RestController
@RequestMapping("/api/users")
public class UserController {

  @GetMapping("/page/{page}")
  public ResponseEntity<PageModel<User>> getAllUsers(@PathVariable("page") @NotNull Integer page,
      @NotNull @RequestParam("size") Integer size,
      @RequestParam("filt") @Nullable String filt, @RequestParam("ordr") @Nullable String ordr,
      @RequestParam("dir") @Nullable Boolean dir) {
    return null;
  }

  @GetMapping("/{id}")
  public ResponseEntity<User> getOneUser(@PathVariable("id") Integer id) {
    return null;
  }

  @PutMapping("/{id}")
  public ResponseEntity<Message> updateUser(@PathVariable("id") Integer id, @RequestBody String user) {
    return null;
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Message> deleteUser(@PathVariable("id") Integer id) {
    return null;
  }
}
