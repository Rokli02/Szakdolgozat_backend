package hu.marko.szakdolgozat.spring.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

import hu.marko.szakdolgozat.spring.controller.model.newModel.NewSeries;
import hu.marko.szakdolgozat.spring.controller.model.updateModel.UpdateSeries;
import hu.marko.szakdolgozat.spring.controller.model.wrapper.SeriesWrapper;
import hu.marko.szakdolgozat.spring.controller.model.wrapper.SeriesesWrapper;
import hu.marko.szakdolgozat.spring.service.SeriesService;
import hu.marko.szakdolgozat.spring.service.model.PageModel;
import lombok.AllArgsConstructor;
import hu.marko.szakdolgozat.spring.controller.model.Message;
import hu.marko.szakdolgozat.spring.controller.model.Series;

@AllArgsConstructor
@RestController
@RequestMapping("/api/serieses")
public class SeriesController {
  private SeriesService seriesService;

  @GetMapping("/page/{page}")
  public ResponseEntity<SeriesesWrapper> getAllSerieses(@PathVariable("page") @NotNull Integer page,
      @NotNull @RequestParam("size") Integer size,
      @RequestParam("filt") @Nullable String filt, @RequestParam("ordr") @Nullable String ordr,
      @RequestParam("dir") @Nullable Boolean dir) {
    PageModel<hu.marko.szakdolgozat.spring.service.model.Series> pageModel = seriesService
        .findByPageAndSizeAndFilterAndOrder(page, size, filt, ordr, dir);
    List<Series> seriesList = StreamSupport.stream(pageModel.getModels().spliterator(), false).map(Series::new)
        .collect(Collectors.toList());
    return ResponseEntity.ok().body(new SeriesesWrapper(seriesList, pageModel.getCount()));
  }

  @GetMapping("/{id}")
  public ResponseEntity<SeriesWrapper> getOneSeries(@PathVariable("id") @NotNull Long id) {
    return ResponseEntity.ok().body(new SeriesWrapper(new Series(seriesService.findOne(id))));
  }

  @PostMapping("")
  public ResponseEntity<SeriesWrapper> saveSeries(@RequestBody @NotNull NewSeries newSeries) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new SeriesWrapper(new Series(seriesService.save(newSeries.toServiceSeries()))));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Message> updateSeries(@PathVariable("id") @NotNull Long id,
      @RequestBody @NotNull UpdateSeries series) {
    if (seriesService.update(id, series.toServiceSeries())) {
      return ResponseEntity.ok().body(new Message("Series update is successful!"));
    }
    return ResponseEntity.badRequest().body(new Message("Couldn't update series!"));
  }

  @DeleteMapping("/image/{id}")
  public ResponseEntity<Message> deleteImage(@PathVariable("id") @NotNull Long id) {
    return null;
  }
}
