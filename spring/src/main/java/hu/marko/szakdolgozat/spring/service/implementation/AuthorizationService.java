package hu.marko.szakdolgozat.spring.service.implementation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import hu.marko.szakdolgozat.spring.controller.model.auth.LoginUser;
import hu.marko.szakdolgozat.spring.exception.BadRequestException;
import hu.marko.szakdolgozat.spring.exception.NotFoundException;
import hu.marko.szakdolgozat.spring.filter.model.UserDetailsModel;
import hu.marko.szakdolgozat.spring.repository.RoleRepository;
import hu.marko.szakdolgozat.spring.repository.UserRepository;
import hu.marko.szakdolgozat.spring.service.model.Role;
import hu.marko.szakdolgozat.spring.service.model.User;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AuthorizationService
    implements hu.marko.szakdolgozat.spring.service.AuthorizationService, UserDetailsService {
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder encoder;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<hu.marko.szakdolgozat.spring.repository.model.User> oUser = userRepository
        .findByUniqueName(username);
    if (!oUser.isPresent()) {
      throw new NotFoundException("There is no user with such username or email!");
    }
    Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
    authorities.add(new SimpleGrantedAuthority(oUser.get().getRole().getName()));
    return new UserDetailsModel(oUser.get().getId(),
        new LoginUser(oUser.get().getName(), oUser.get().getUsername(), oUser.get().getEmail(),
            new hu.marko.szakdolgozat.spring.controller.model.Role(new Role(oUser.get().getRole())),
            oUser.get().getCreated().toString()),
        oUser.get().getUsername(), oUser.get().getPassword(), authorities);
  }

  @Override
  public Boolean signup(User user) {
    Optional<hu.marko.szakdolgozat.spring.repository.model.User> oUser = userRepository
        .findByUsernameOrEmail(user.getUsername(), user.getEmail());
    if (oUser.isPresent()) {
      throw new BadRequestException("Username or email is already in use!");
    }
    Optional<hu.marko.szakdolgozat.spring.repository.model.Role> oRole = roleRepository.findById(1L);
    if (!oRole.isPresent()) {
      throw new NotFoundException("Role doesn't exists!");
    }

    hu.marko.szakdolgozat.spring.repository.model.User newUser = new hu.marko.szakdolgozat.spring.repository.model.User();
    newUser.setName(user.getName());
    newUser.setUsername(user.getUsername());
    newUser.setEmail(user.getEmail());
    newUser.setBirthdate(user.getBirthdate());
    newUser.setPassword(encoder.encode(user.getPassword()));
    newUser.setRole(oRole.get());
    newUser.setActive(true);

    hu.marko.szakdolgozat.spring.repository.model.User response = userRepository.save(newUser);
    if (response != null) {
      return true;
    }

    return false;
  }

  @Override
  public User validUser(Long id, String username, String email) {
    Optional<hu.marko.szakdolgozat.spring.repository.model.User> oUser = userRepository
        .findByIdAndUsernameAndEmailAndActive(id, username, email);
    if (oUser.isPresent()) {
      return new User(oUser.get());
    }

    return null;
  }

  @Override
  public Boolean hasRight(User user, String nameOfRight) {
    return user.getRole().getName() == nameOfRight;
  }

  @Override
  public List<Role> findAllRole() {
    return StreamSupport.stream(roleRepository.findAll().spliterator(), false)
        .map(Role::new).collect(Collectors.toList());
  }
}
