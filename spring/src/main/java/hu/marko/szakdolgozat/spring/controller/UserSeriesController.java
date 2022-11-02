package hu.marko.szakdolgozat.spring.controller;

import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hu.marko.szakdolgozat.spring.controller.model.newModel.NewUserSeries;
import hu.marko.szakdolgozat.spring.controller.model.Message;
import hu.marko.szakdolgozat.spring.controller.model.PageModel;
import hu.marko.szakdolgozat.spring.controller.model.UserSeries;

@RestController
@RequestMapping("/api/user/series")
public class UserSeriesController {

  @GetMapping("/page/{page}")
  public ResponseEntity<PageModel<UserSeries>> getAllUserSerieses(@PathVariable("page") @NotNull Integer page,
      @NotNull @RequestParam("size") Integer size, @RequestParam("stat") @Nullable Integer stat,
      @RequestParam("filt") @Nullable String filt, @RequestParam("ordr") @Nullable String ordr,
      @RequestParam("dir") @Nullable Boolean dir) {
    return null;
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserSeries> getOneUserSeries(@PathVariable("id") @NotNull Integer seriesId) {
    return null;
  }

  @PostMapping("")
  public ResponseEntity<UserSeries> saveUserSeries(@RequestBody @NotNull NewUserSeries newUserSeries) {
    return null;
  }

  @PutMapping("/{id}")
  public ResponseEntity<Message> updateUserSeries(@PathVariable("id") @NotNull Integer seriesId,
      @RequestBody @NotNull UserSeries userSeries) {
    return null;
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Message> deleteUserSeries(@PathVariable("id") @NotNull Integer seriesId) {
    return null;
  }
}
