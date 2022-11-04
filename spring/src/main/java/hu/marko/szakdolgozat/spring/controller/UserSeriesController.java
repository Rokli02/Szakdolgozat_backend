package hu.marko.szakdolgozat.spring.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
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
import hu.marko.szakdolgozat.spring.controller.model.wrapper.UserseriesWrapper;
import hu.marko.szakdolgozat.spring.controller.model.wrapper.UserseriesesWrapper;
import hu.marko.szakdolgozat.spring.service.UserSeriesService;
import hu.marko.szakdolgozat.spring.service.model.PageModel;
import lombok.AllArgsConstructor;
import hu.marko.szakdolgozat.spring.controller.model.Message;
import hu.marko.szakdolgozat.spring.controller.model.UserSeries;

@AllArgsConstructor
@RestController
@RequestMapping("/api/user/series")
public class UserSeriesController {
  private UserSeriesService userSeriesService;

  @GetMapping("/page/{page}")
  public ResponseEntity<UserseriesesWrapper> getAllUserSerieses(@PathVariable("page") @NotNull Integer page,
      @NotNull @RequestParam("size") Integer size, @RequestParam("stat") @Nullable Long stat,
      @RequestParam("filt") @Nullable String filt, @RequestParam("ordr") @Nullable String ordr,
      @RequestParam("dir") @Nullable Boolean dir, HttpServletRequest request) {
    Long userId = (Long) request.getAttribute("userId");
    PageModel<hu.marko.szakdolgozat.spring.service.model.UserSeries> pageModel = userSeriesService
        .findByPageAndSizeAndFilterAndStatusAndOrder(userId, page, size, filt, stat, ordr, dir);
    List<UserSeries> userseriesList = StreamSupport.stream(pageModel.getModels().spliterator(), false)
        .map(UserSeries::new)
        .collect(Collectors.toList());
    return ResponseEntity.ok().body(new UserseriesesWrapper(userseriesList, pageModel.getCount()));
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserseriesWrapper> getOneUserSeries(@PathVariable("id") @NotNull Long seriesId,
      HttpServletRequest request) {
    Long userId = (Long) request.getAttribute("userId");
    return ResponseEntity.ok().body(new UserseriesWrapper(new UserSeries(userSeriesService.findOne(userId, seriesId))));
  }

  @PostMapping("")
  public ResponseEntity<UserseriesWrapper> saveUserSeries(@RequestBody @NotNull NewUserSeries newUserSeries,
      HttpServletRequest request) {
    Long userId = (Long) request.getAttribute("userId");
    return ResponseEntity.status(HttpStatus.CREATED).body(
        new UserseriesWrapper(new UserSeries(userSeriesService.save(userId, newUserSeries.toServiceUserseries()))));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Message> updateUserSeries(@PathVariable("id") @NotNull Long seriesId,
      @RequestBody @NotNull UserSeries userSeries, HttpServletRequest request) {
    Long userId = (Long) request.getAttribute("userId");
    if (userSeriesService.update(userId, seriesId, userSeries.toServiceUserseries())) {
      return ResponseEntity.ok().body(new Message("Userseries is updated succesfully!"));
    }
    return ResponseEntity.badRequest().body(new Message("Something went wrong during update!"));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Message> deleteUserSeries(@PathVariable("id") @NotNull Long seriesId,
      HttpServletRequest request) {
    Long userId = (Long) request.getAttribute("userId");
    if (userSeriesService.remove(userId, seriesId) != null) {
      return ResponseEntity.ok().body(new Message("Userseries is removed succesfully!"));
    }
    return ResponseEntity.badRequest().body(new Message("Something went wrong during remove!"));
  }
}
