package hu.marko.szakdolgozat.spring.controller;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.Valid;
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
import hu.marko.szakdolgozat.spring.controller.model.User;
import hu.marko.szakdolgozat.spring.controller.model.wrapper.UserWrapper;
import hu.marko.szakdolgozat.spring.controller.model.wrapper.UsersWrapper;
import hu.marko.szakdolgozat.spring.service.UserService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {
  private UserService userService;

  @GetMapping("/page/{page}")
  public ResponseEntity<UsersWrapper> getAllUsers(@PathVariable("page") @NotNull Integer page,
      @NotNull @RequestParam("size") Integer size,
      @RequestParam("filt") @Nullable String filt, @RequestParam("ordr") @Nullable String ordr,
      @RequestParam("dir") @Nullable Boolean dir) {
    hu.marko.szakdolgozat.spring.service.model.PageModel<hu.marko.szakdolgozat.spring.service.model.User> sPageModel = userService
        .findByPageAndSizeAndFilterAndOrder(page, size, filt, ordr, dir);
    return ResponseEntity.ok().body(new UsersWrapper(
        StreamSupport.stream(sPageModel.getModels().spliterator(), false).map(User::new).collect(Collectors.toList()),
        sPageModel.getCount()));
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserWrapper> getOneUser(@PathVariable("id") @NotNull Long id) {
    return ResponseEntity.ok().body(new UserWrapper(new User(userService.findOne(id))));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Message> updateUser(@PathVariable("id") Long id, @RequestBody @Valid User user) {
    if (userService.update(id, user.toServiceUser())) {
      return ResponseEntity.ok().body(new Message("User is modified succesfully!"));
    }
    return ResponseEntity.badRequest().body(new Message("Couldn't modify user!"));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Message> deleteUser(@PathVariable("id") Long id) {
    if (userService.remove(id) != null) {
      return ResponseEntity.ok().body(new Message("User is deactivated!"));
    }
    return ResponseEntity.badRequest().body(new Message("Couldn't deactivate user!"));
  }
}
