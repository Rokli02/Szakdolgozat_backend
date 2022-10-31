package hu.marko.szakdolgozat.spring.controller;

import javax.validation.constraints.NotNull;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hu.marko.szakdolgozat.spring.controller.model.PageModel;
import hu.marko.szakdolgozat.spring.controller.model.User;

@RestController
@RequestMapping("/api/users")
public class UserController {

  @GetMapping("/page/:page")
  public PageModel<User> getAllUsers(@PathVariable("page") @NotNull Integer page,
      @NotNull @RequestParam("size") Integer size,
      @RequestParam("filt") String filt, @RequestParam("ordr") String ordr, @RequestParam("dir") Boolean dir) {
    return null;
  }

  @GetMapping("/:id")
  public User getOneUser(@PathVariable("id") Integer id) {
    return new User();
  }

  @PutMapping("/:id")
  public String updateUser(@PathVariable("id") Integer id, @RequestBody String user) {
    return "updateUser";
  }

  @DeleteMapping("/:id")
  public String deleteUser(@PathVariable("id") Integer id) {
    return "deleteUser";
  }
}
