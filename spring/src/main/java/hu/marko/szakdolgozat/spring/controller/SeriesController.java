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

import hu.marko.szakdolgozat.spring.controller.model.newModel.NewSeries;
import hu.marko.szakdolgozat.spring.controller.model.Message;
import hu.marko.szakdolgozat.spring.controller.model.Series;

@RestController
@RequestMapping("/api/serieses")
public class SeriesController {

  @GetMapping("/page/{page}")
  public ResponseEntity<String> getAllSerieses(@PathVariable("page") @NotNull Integer page,
      @NotNull @RequestParam("size") Integer size,
      @RequestParam("filt") @Nullable String filt, @RequestParam("ordr") @Nullable String ordr,
      @RequestParam("dir") @Nullable Boolean dir) {
    return null;
  }

  @GetMapping("/{id}")
  public ResponseEntity<Series> getOneSeries(@PathVariable("id") @NotNull Integer id) {
    return null;
  }

  @PostMapping("")
  public ResponseEntity<Series> saveSeries(@RequestBody @NotNull NewSeries newSeries) {
    return null;
  }

  @PutMapping("/{id}")
  public ResponseEntity<Message> updateSeries(@PathVariable("id") @NotNull Integer id,
      @RequestBody @NotNull Series series) {
    return null;
  }

  @DeleteMapping("/image/{id}")
  public ResponseEntity<Message> deleteImage(@PathVariable("id") @NotNull Integer id) {
    return null;
  }
}
