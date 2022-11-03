package hu.marko.szakdolgozat.spring.repository;

import org.springframework.data.repository.CrudRepository;

import hu.marko.szakdolgozat.spring.repository.model.Season;

public interface SeasonRepository extends CrudRepository<Season, Long> {

}
