package hu.marko.szakdolgozat.spring.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import hu.marko.szakdolgozat.spring.repository.model.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
  @Query(value = "SELECT * FROM user WHERE (email = :name OR username = :name) AND active=1 LIMIT 1", nativeQuery = true)
  Optional<User> findByUniqueName(String name);

  Optional<User> findByUsernameOrEmail(String username, String email);

  @Query(value = "SELECT * FROM user WHERE id = :id AND username = :username AND email = :email AND active=1 LIMIT 1", nativeQuery = true)
  Optional<User> findByIdAndUsernameAndEmailAndActive(Long id, String username, String email);

  @Query(value = "SELECT * FROM user u INNER JOIN role r ON r.id = u.f_role_id WHERE u.name LIKE %:filter% OR u.birthdate LIKE %:filter% OR u.username LIKE %:filter% OR u.email LIKE %:filter% OR r.name LIKE %:filter% GROUP BY username", countQuery = "SELECT count(*) FROM user u INNER JOIN role r ON r.id = u.f_role_id WHERE u.name LIKE %:filter% OR u.birthdate LIKE %:filter% OR u.username LIKE %:filter% OR u.email LIKE %:filter% OR r.name LIKE %:filter% GROUP BY username", nativeQuery = true)
  Page<User> findWithPagination(Pageable page, String filter);

}
