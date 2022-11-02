package hu.marko.szakdolgozat.spring.filter.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import hu.marko.szakdolgozat.spring.controller.model.auth.LoginUser;

public class UserDetailsModel implements UserDetails {
  private String username;
  private String password;
  private Collection<? extends GrantedAuthority> authorities;
  private Long id;
  private LoginUser loginUser;

  public UserDetailsModel(String username, String password, Collection<? extends GrantedAuthority> authorities) {
    super();
    this.username = username;
    this.password = password;
    this.authorities = authorities;
  }

  public UserDetailsModel(Long id, LoginUser loginUser, String username, String password,
      Collection<? extends GrantedAuthority> authorities) {
    this(username, password, authorities);
    this.loginUser = loginUser;
    this.id = id;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  public Long getId() {
    return id;
  }

  public LoginUser getLoginUser() {
    return loginUser;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

}
