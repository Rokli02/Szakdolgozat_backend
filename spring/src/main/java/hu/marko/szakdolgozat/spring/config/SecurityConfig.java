package hu.marko.szakdolgozat.spring.config;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
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
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CharacterEncodingFilter;

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
    CharacterEncodingFilter filter = new CharacterEncodingFilter();
    filter.setEncoding("UTF-8");
    filter.setForceEncoding(true);

    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        .authorizeRequests().antMatchers(HttpMethod.GET, "/api/categories").permitAll()
        .antMatchers(HttpMethod.GET, "/api/statuses").permitAll()
        .antMatchers(HttpMethod.GET, "/api/serieses/**").permitAll()
        .antMatchers(HttpMethod.GET, "/api/newsfeeds/**").permitAll()
        .antMatchers("/api/auth/**").permitAll()

        .antMatchers(HttpMethod.GET, "/api/newsfeeds/personal/**").hasAuthority("user")
        .antMatchers("/api/user/series/**").hasAuthority("user")

        .antMatchers("/api/users/**").hasAuthority("admin")

        .antMatchers(HttpMethod.POST, "/api/images").hasAnyAuthority("admin", "siteManager")
        .antMatchers("/api/statuses/**").hasAnyAuthority("admin", "siteManager")
        .antMatchers("/api/serieses/**").hasAnyAuthority("admin", "siteManager")
        .antMatchers("/api/newsfeeds/**").hasAnyAuthority("admin", "siteManager")
        .antMatchers("/api/categories/**").hasAnyAuthority("admin", "siteManager").and()

        .addFilter(new AuthenticationFilter(AuthenticationManagerBean()))
        .addFilterBefore(new AuthorizationFilter(authService), UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(filter, CsrfFilter.class)
        .cors(Customizer.withDefaults())
        .csrf().disable();

    return http.build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration corsConfig = new CorsConfiguration();
    corsConfig.setAllowedOrigins(Arrays.asList("*"));
    corsConfig.setMaxAge(3600L);
    corsConfig.addAllowedMethod("*");
    corsConfig.addAllowedHeader("*");
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfig);
    return source;
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
