package hu.marko.szakdolgozat.spring.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.marko.szakdolgozat.spring.controller.model.Message;
import hu.marko.szakdolgozat.spring.controller.model.Role;
import hu.marko.szakdolgozat.spring.controller.model.newModel.NewUser;
import hu.marko.szakdolgozat.spring.service.AuthorizationService;
import hu.marko.szakdolgozat.spring.service.model.User;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthorizationController {
  private final AuthorizationService authService;

  @GetMapping("/roles")
  public ResponseEntity<List<Role>> getAllRoles() {
    return ResponseEntity.ok().body(StreamSupport.stream(authService.findAllRole().spliterator(), false).map(Role::new)
        .collect(Collectors.toList()));
  }

  @PostMapping("/signup")
  public ResponseEntity<Message> signup(@RequestBody @NotNull NewUser newUser) {
    if (authService.signup(new User(null, newUser.getName(), newUser.getUsername(), newUser.getEmail(),
        newUser.getBirthdate(), newUser.getPassword(), null, null, null))) {
      return ResponseEntity.status(HttpStatus.CREATED).body(new Message("Signed up successfully!"));
    }
    return ResponseEntity.badRequest().body(new Message("Unsuccessful registration!"));
  }
}
