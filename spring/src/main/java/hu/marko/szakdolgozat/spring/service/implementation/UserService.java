package hu.marko.szakdolgozat.spring.service.implementation;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import hu.marko.szakdolgozat.spring.exception.NotFoundException;
import hu.marko.szakdolgozat.spring.repository.RoleRepository;
import hu.marko.szakdolgozat.spring.repository.UserRepository;
import hu.marko.szakdolgozat.spring.service.model.User;
import lombok.AllArgsConstructor;
import hu.marko.szakdolgozat.spring.service.model.PageModel;

@AllArgsConstructor
@Service
public class UserService implements hu.marko.szakdolgozat.spring.service.UserService {
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder encoder;

  @Override
  public PageModel<User> findByPageAndSizeAndFilterAndOrder(Integer page, Integer size, String filter, String order,
      Boolean ascendingDirection) {

    Direction direction;
    if (ascendingDirection == null || !ascendingDirection) {
      direction = Direction.DESC;
    } else {
      direction = Direction.ASC;
    }

    String interFilter = "";
    if (filter != null) {
      interFilter = filter;
    }
    String interOrder = "id";
    if (order != null) {
      interOrder = order;
    }

    Page<hu.marko.szakdolgozat.spring.repository.model.User> pagedEntity = userRepository
        .findWithPagination(PageRequest.of(page - 1, size, Sort.by(direction, interOrder)), interFilter);

    List<User> userList = StreamSupport.stream(pagedEntity.getContent().spliterator(), false).map((user) -> {
      User tempUser = new User(user);
      tempUser.setPassword(null);
      return tempUser;
    }).collect(Collectors.toList());
    PageModel<User> pageModel = new PageModel<User>(userList, pagedEntity.getTotalElements());
    return pageModel;
  }

  @Override
  public User findOne(Long id) {
    Optional<hu.marko.szakdolgozat.spring.repository.model.User> oUser = userRepository.findById(id);
    if (!oUser.isPresent()) {
      throw new NotFoundException("There is no such user!");
    }

    return new User(oUser.get());
  }

  @Override
  public Boolean update(Long id, User user) {
    Optional<hu.marko.szakdolgozat.spring.repository.model.User> oUser = userRepository.findById(id);
    if (!oUser.isPresent()) {
      throw new NotFoundException("There is no such user!");
    }
    Optional<hu.marko.szakdolgozat.spring.repository.model.Role> oRole = roleRepository
        .findById(user.getRole().getId());
    hu.marko.szakdolgozat.spring.repository.model.User entity = oUser.get();

    if (user.getName() != null) {
      entity.setName(user.getName());
    }
    if (user.getEmail() != null) {
      entity.setEmail(user.getEmail());
    }
    if (user.getUsername() != null) {
      entity.setUsername(user.getUsername());
    }
    if (user.getBirthdate() != null) {
      entity.setBirthdate(user.getBirthdate());
    }
    if (user.getPassword() != null) {
      entity.setPassword(encoder.encode(user.getPassword()));
    }
    if (user.getActive() != null) {
      entity.setActive(user.getActive());
    }
    if (oRole.isPresent()) {
      entity.setRole(oRole.get());
    }

    hu.marko.szakdolgozat.spring.repository.model.User updatedUser = userRepository.save(entity);
    if (updatedUser != null) {
      return true;
    }

    return false;
  }

  @Override
  public Long remove(Long id) {
    Optional<hu.marko.szakdolgozat.spring.repository.model.User> oUser = userRepository.findById(id);
    if (!oUser.isPresent()) {
      throw new NotFoundException("There is no such user!");
    }
    hu.marko.szakdolgozat.spring.repository.model.User entity = oUser.get();
    entity.setActive(false);

    hu.marko.szakdolgozat.spring.repository.model.User response = userRepository.save(entity);
    return response.getId();
  }

}
