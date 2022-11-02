package hu.marko.szakdolgozat.spring.config;

import java.util.Collection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import hu.marko.szakdolgozat.spring.exception.BadRequestException;
import hu.marko.szakdolgozat.spring.filter.AuthenticationFilter;
import hu.marko.szakdolgozat.spring.filter.AuthorizationFilter;
import hu.marko.szakdolgozat.spring.filter.model.UserDetailsModel;
import hu.marko.szakdolgozat.spring.service.AuthorizationService;
import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
  private final UserDetailsService userDetailsService;
  private final PasswordEncoder passwordEncoder;
  private final AuthorizationService authService;

  @Bean
  @Order(1)
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf().disable();
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/categories").permitAll();
    http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/statuses").permitAll();
    http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/serieses/**").permitAll();
    http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/newsfeeds/**").permitAll();
    http.authorizeRequests().antMatchers("/api/auth/**").permitAll();

    http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/newsfeeds/personal/**").hasAuthority("user");
    http.authorizeRequests().antMatchers("/api/user/series/**").hasAuthority("user");

    http.authorizeRequests().antMatchers("/api/users/**").hasAuthority("admin");

    http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/images").hasAnyAuthority("admin", "siteManager");
    http.authorizeRequests().antMatchers("/api/statuses/**").hasAnyAuthority("admin", "siteManager");
    http.authorizeRequests().antMatchers("/api/serieses/**").hasAnyAuthority("admin", "siteManager");
    http.authorizeRequests().antMatchers("/api/newsfeeds/**").hasAnyAuthority("admin", "siteManager");
    http.authorizeRequests().antMatchers("/api/categories/**").hasAnyAuthority("admin", "siteManager");

    http.addFilter(new AuthenticationFilter(AuthenticationManagerBean()));
    http.addFilterBefore(new AuthorizationFilter(authService), UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public AuthenticationManager AuthenticationManagerBean() {
    return new AuthenticationManager() {
      @Override
      public Authentication authenticate(Authentication auth) throws AuthenticationException {
        UserDetailsModel userDetailsModel = (UserDetailsModel) userDetailsService
            .loadUserByUsername(auth.getPrincipal().toString());
        String rawPassword = auth.getCredentials().toString();
        if (!passwordEncoder.matches(rawPassword, userDetailsModel.getPassword())) {
          throw new BadRequestException("Password is not correct!");
        } else {
          return new Authentication() {
            @Override
            public String getName() {
              return userDetailsModel.getLoginUser().getName();
            }

            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
              return userDetailsModel.getAuthorities();
            }

            @Override
            public Object getCredentials() {
              return null;
            }

            @Override
            public Object getDetails() {
              return userDetailsModel.getLoginUser();
            }

            @Override
            public Object getPrincipal() {
              return userDetailsModel;
            }

            @Override
            public boolean isAuthenticated() {
              return true;
            }

            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
            }
          };
        }
      }

    };
  }
}
