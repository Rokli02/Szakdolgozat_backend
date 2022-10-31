package hu.marko.szakdolgozat.spring.controller;

import javax.validation.constraints.NotNull;

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
import hu.marko.szakdolgozat.spring.controller.model.PageModel;
import hu.marko.szakdolgozat.spring.controller.model.UserSeries;

@RestController
@RequestMapping("/api/user/series")
public class UserSeriesController {

  @GetMapping("/page/:page")
  public PageModel<UserSeries> getAllUserSerieses(@PathVariable("page") @NotNull Integer page,
      @NotNull @RequestParam("size") Integer size, @RequestParam("stat") Integer stat,
      @RequestParam("filt") String filt, @RequestParam("ordr") String ordr, @RequestParam("dir") Boolean dir) {
    return null;
  }

  @GetMapping("/:id")
  public UserSeries getOneUserSeries(@PathVariable("id") @NotNull Integer seriesId) {
    return null;
  }

  @PostMapping("/")
  public UserSeries saveUserSeries(@RequestBody @NotNull NewUserSeries newUserSeries) {
    return null;
  }

  @PutMapping("/:id")
  public String updateUserSeries(@PathVariable("id") @NotNull Integer seriesId,
      @RequestBody @NotNull UserSeries userSeries) {
    return "updateUserSeries";
  }

  @DeleteMapping("/:id")
  public String deleteUserSeries(@PathVariable("id") @NotNull Integer seriesId) {
    return "deleteUserSeries";
  }
}
