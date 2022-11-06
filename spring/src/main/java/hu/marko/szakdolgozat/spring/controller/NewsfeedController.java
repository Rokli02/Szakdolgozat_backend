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

import hu.marko.szakdolgozat.spring.controller.model.Message;
import hu.marko.szakdolgozat.spring.controller.model.Newsfeed;
import hu.marko.szakdolgozat.spring.controller.model.newModel.NewNewsfeed;
import hu.marko.szakdolgozat.spring.controller.model.wrapper.NewsfeedWrapper;
import hu.marko.szakdolgozat.spring.controller.model.wrapper.NewsfeedsWrapper;
import hu.marko.szakdolgozat.spring.service.NewsfeedService;
import hu.marko.szakdolgozat.spring.service.model.PageModel;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/newsfeeds")
public class NewsfeedController {
  private NewsfeedService newsfeedService;

  @GetMapping("/page/{page}")
  public ResponseEntity<NewsfeedsWrapper> getAllNewsfeeds(@PathVariable("page") @NotNull Integer page,
      @NotNull @RequestParam("size") Integer size,
      @RequestParam("filt") @Nullable String filt, @RequestParam("ordr") @Nullable String ordr,
      @RequestParam("dir") @Nullable Boolean dir) {
    PageModel<hu.marko.szakdolgozat.spring.service.model.Newsfeed> pageModel = newsfeedService
        .findByPageAndSizeAndFilterAndOrder(page, size, filt, ordr, dir);
    List<Newsfeed> newsfeedList = StreamSupport.stream(pageModel.getModels().spliterator(), false).map(Newsfeed::new)
        .collect(Collectors.toList());
    return ResponseEntity.ok().body(new NewsfeedsWrapper(newsfeedList, pageModel.getCount()));
  }

  @GetMapping("/{id}")
  public ResponseEntity<NewsfeedWrapper> getOneNewsfeed(@PathVariable("id") @NotNull Long id) {
    return ResponseEntity.ok().body(new NewsfeedWrapper(new Newsfeed(newsfeedService.findOne(id))));
  }

  @GetMapping("/personal/page/{page}")
  public ResponseEntity<NewsfeedsWrapper> getAllPersonalNewsfeeds(@PathVariable("page") @NotNull Integer page,
      @NotNull @RequestParam("size") Integer size,
      @RequestParam("filt") @Nullable String filt, @RequestParam("ordr") @Nullable String ordr,
      @RequestParam("dir") @Nullable Boolean dir, HttpServletRequest request) {
    Long userId = (Long) request.getAttribute("userId");
    PageModel<hu.marko.szakdolgozat.spring.service.model.Newsfeed> pageModel = newsfeedService
        .findByUserAndPageAndSizeAndFilterAndOrder(userId, page, size, filt, ordr, dir);
    List<Newsfeed> newsfeedList = StreamSupport.stream(pageModel.getModels().spliterator(), false).map(Newsfeed::new)
        .collect(Collectors.toList());
    return ResponseEntity.ok().body(new NewsfeedsWrapper(newsfeedList, pageModel.getCount()));
  }

  @PostMapping("/edit")
  public ResponseEntity<NewsfeedWrapper> saveNewsfeed(@RequestBody @NotNull NewNewsfeed newNewsfeed) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new NewsfeedWrapper(new Newsfeed(newsfeedService.save(newNewsfeed.toServiceNewsfeed()))));
  }

  @PutMapping("/edit/{id}")
  public ResponseEntity<Message> updateNewsfeed(@PathVariable("id") @NotNull Long id,
      @RequestBody @NotNull Newsfeed newsfeed) {
    if (newsfeedService.update(id, newsfeed.toServiceNewsfeed())) {
      return ResponseEntity.ok().body(new Message("Newsfeed update is successful!"));
    }
    return ResponseEntity.badRequest().body(new Message("Couldn't update newsfeed!"));
  }

  @DeleteMapping("/edit/{id}")
  public ResponseEntity<Message> deleteNewsfeed(@PathVariable("id") @NotNull Long id) {
    if (newsfeedService.remove(id) != null) {
      return ResponseEntity.ok().body(new Message("Newsfeed is deleted successfully!"));
    }
    return ResponseEntity.badRequest().body(new Message("Couldn't delete newsfeed!"));
  }
}
