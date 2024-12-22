package com.example.explorecalijpa.web;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.explorecalijpa.business.TourRatingService;
import com.example.explorecalijpa.model.TourRating;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Tour Rating Controller
 *
 * Created by Mary Ellen Bowman
 */
@RestController
@RequestMapping(path = "/tours/{tourId}/ratings")
public class TourRatingController {
  private TourRatingService tourRatingService;

  public TourRatingController(TourRatingService tourRatingService) {
    this.tourRatingService = tourRatingService;
  }

  @GetMapping("/")
  @ResponseStatus(HttpStatus.OK)
  public List<RatingDto> getRatingsForTour(@PathVariable(value = "tourId") int tourId) {
    return tourRatingService.lookupRatingById(tourId).stream().map(RatingDto::new).collect(Collectors.toList());
  }

  @PostMapping("/")
  @ResponseStatus(HttpStatus.CREATED)
  public TourRating createRating(@PathVariable(value = "tourId") int tourId, @RequestBody @Valid RatingDto ratingDto) {
    return tourRatingService.createNew(tourId, ratingDto.getCustomerId(), ratingDto.getScore(), ratingDto.getComment());
  }

  @ExceptionHandler(NoSuchElementException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public String a404(NoSuchElementException noSuchElementException) {
    return noSuchElementException.getMessage();
  }
}
