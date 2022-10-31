package hu.marko.szakdolgozat.spring.service;

import java.util.List;

import hu.marko.szakdolgozat.spring.service.model.Status;

public interface StatusService {
  List<Status> findAll();

  Status save(Status entity);

  boolean update(int id, Status entity);
}
