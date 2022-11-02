package hu.marko.szakdolgozat.spring.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import hu.marko.szakdolgozat.spring.repository.model.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
  @Query(value = "SELECT * FROM user WHERE (email = :name OR username = :name) AND active=1 LIMIT 1", nativeQuery = true)
  Optional<User> findByUniqueName(String name);

  Optional<User> findByUsernameOrEmail(String username, String email);

  @Query(value = "SELECT * FROM user WHERE id = :id AND username = :username AND email = :email AND active=1 LIMIT 1", nativeQuery = true)
  Optional<User> findByIdAndUsernameAndEmailAndActive(Long id, String username, String email);
}
