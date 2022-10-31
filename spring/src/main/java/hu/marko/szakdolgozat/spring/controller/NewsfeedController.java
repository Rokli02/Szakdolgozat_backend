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

import hu.marko.szakdolgozat.spring.controller.model.Newsfeed;
import hu.marko.szakdolgozat.spring.controller.model.PageModel;
import hu.marko.szakdolgozat.spring.controller.model.newModel.NewNewsfeed;

@RestController
@RequestMapping("/api/newsfeeds")
public class NewsfeedController {

  @GetMapping("/page/:page")
  public PageModel<Newsfeed> getAllNewsfeeds(@PathVariable("page") @NotNull Integer page,
      @NotNull @RequestParam("size") Integer size,
      @RequestParam("filt") String filt, @RequestParam("ordr") String ordr, @RequestParam("dir") Boolean dir) {
    return null;
  }

  @GetMapping("/:id")
  public Newsfeed getOneNewsfeed(@PathVariable("id") @NotNull Integer id) {
    return null;
  }

  // Csak user
  @GetMapping("/personal/page/:page")
  public PageModel<Newsfeed> getAllPersonalNewsfeeds(@PathVariable("page") @NotNull Integer page,
      @NotNull @RequestParam("size") Integer size,
      @RequestParam("filt") String filt, @RequestParam("ordr") String ordr, @RequestParam("dir") Boolean dir) {
    return null;
  }

  // Csak siteManager és admin innentől
  @PostMapping("/")
  public Newsfeed saveNewsfeed(@RequestBody @NotNull NewNewsfeed newNewsfeed) {
    return null;
  }

  @PutMapping("/:id")
  public String updateNewsfeed(@PathVariable("id") @NotNull Integer id, @RequestBody @NotNull Newsfeed newsfeed) {
    return "updateNewsfeed";
  }

  @DeleteMapping("/:id")
  public String deleteNewsfeed(@PathVariable("id") @NotNull Integer id) {
    return "deleteNewsfeed";
  }
}
