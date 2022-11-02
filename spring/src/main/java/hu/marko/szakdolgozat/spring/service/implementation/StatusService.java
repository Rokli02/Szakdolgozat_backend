package hu.marko.szakdolgozat.spring.service.implementation;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import hu.marko.szakdolgozat.spring.exception.BadRequestException;
import hu.marko.szakdolgozat.spring.repository.StatusRepository;
import hu.marko.szakdolgozat.spring.service.model.Status;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StatusService implements hu.marko.szakdolgozat.spring.service.StatusService {
  private final StatusRepository statusRepository;

  @Override
  public List<Status> findAll() {
    return StreamSupport.stream(statusRepository.findAll().spliterator(), false)
        .map(Status::new).collect(Collectors.toList());
  }

  @Override
  public Status save(Status status) {
    status.setId(null);
    hu.marko.szakdolgozat.spring.repository.model.Status entity = statusRepository.save(status.toEntity());
    if (entity == null) {
      throw new BadRequestException("Something went wrong during status save!");
    }
    return new Status(entity);
  }

  @Override
  public Boolean update(Long id, Status status) {
    Optional<hu.marko.szakdolgozat.spring.repository.model.Status> oEntity = statusRepository.findById(id);
    if (!oEntity.isPresent()) {
      throw new BadRequestException("Status with such id doesn't exists!!");
    }
    hu.marko.szakdolgozat.spring.repository.model.Status entity = oEntity.get();
    entity.setId(id);
    if (status.getName() != null) {
      entity.setName(status.getName());
    }
    hu.marko.szakdolgozat.spring.repository.model.Status savedEntity = statusRepository.save(entity);
    if (savedEntity == null) {
      throw new BadRequestException("Something went wrong during status update!");
    }

    return true;
  }
}
