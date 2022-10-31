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

import hu.marko.szakdolgozat.spring.controller.model.newModel.NewSeries;
import hu.marko.szakdolgozat.spring.controller.model.PageModel;
import hu.marko.szakdolgozat.spring.controller.model.Series;

@RestController
@RequestMapping("/api/serieses")
public class SeriesController {

  @GetMapping("/page/:page")
  public PageModel<Series> getAllSerieses(@PathVariable("page") @NotNull Integer page,
      @NotNull @RequestParam("size") Integer size,
      @RequestParam("filt") String filt, @RequestParam("ordr") String ordr, @RequestParam("dir") Boolean dir) {
    return null;
  }

  @GetMapping("/:id")
  public Series getOneSeries(@PathVariable("id") @NotNull Integer id) {
    return null;
  }

  @PostMapping("/")
  public Series saveSeries(@RequestBody @NotNull NewSeries newSeries) {
    return null;
  }

  @PutMapping("/:id")
  public String updateSeries(@PathVariable("id") @NotNull Integer id, @RequestBody @NotNull Series series) {
    return "updateSeries";
  }

  @DeleteMapping("/image/:id")
  public String deleteImage(@PathVariable("id") @NotNull Integer id) {
    return "deleteImage";
  }
}
