package hu.marko.szakdolgozat.spring.controller;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.marko.szakdolgozat.spring.controller.model.Role;
import hu.marko.szakdolgozat.spring.controller.model.auth.Login;
import hu.marko.szakdolgozat.spring.controller.model.auth.LoginData;
import hu.marko.szakdolgozat.spring.controller.model.newModel.NewUser;

@RestController
@RequestMapping("/api/auth")
public class AuthorizationController {

  @GetMapping("/roles")
  public List<Role> getAllRoles() {
    return null;
  }

  @PostMapping("/login")
  public LoginData login(@RequestBody @NotNull Login login) {
    return null;
  }

  @PostMapping("/signup")
  public String signup(@RequestBody @NotNull NewUser newUser) {
    return "signup";
  }
}
