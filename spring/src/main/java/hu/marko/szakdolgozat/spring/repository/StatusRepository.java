package hu.marko.szakdolgozat.spring.repository;

import org.springframework.data.repository.CrudRepository;

import hu.marko.szakdolgozat.spring.repository.model.Status;

public interface StatusRepository extends CrudRepository<Status, Long> {

}
