package hu.marko.szakdolgozat.spring.service;

import java.util.List;

import hu.marko.szakdolgozat.spring.service.model.Status;

public interface StatusService {
  List<Status> findAll();

  Status save(Status entity);

  Boolean update(Long id, Status entity);
}
