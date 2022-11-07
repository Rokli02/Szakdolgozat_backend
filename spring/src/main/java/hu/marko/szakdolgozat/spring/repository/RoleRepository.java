package hu.marko.szakdolgozat.spring.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import hu.marko.szakdolgozat.spring.repository.model.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
  Optional<Role> findByName(String name);
}
