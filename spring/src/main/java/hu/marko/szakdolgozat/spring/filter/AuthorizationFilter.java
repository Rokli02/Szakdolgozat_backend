package hu.marko.szakdolgozat.spring.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.databind.ObjectMapper;

import hu.marko.szakdolgozat.spring.controller.model.Message;
import hu.marko.szakdolgozat.spring.service.AuthorizationService;
import hu.marko.szakdolgozat.spring.service.model.User;

public class AuthorizationFilter extends OncePerRequestFilter {
  private final String JWT_TOKEN_SECRET = "secrEtOfJWTokN_123456";
  private AuthorizationService authService;

  public AuthorizationFilter(AuthorizationService authService) {
    this.authService = authService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    if (request.getServletPath().contains("api/auth")) {
      filterChain.doFilter(request, response);
    } else {
      String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
      if (authHeader != null && authHeader.startsWith("Bearer ")) {
        try {
          String token = authHeader.substring("Bearer ".length());
          Algorithm algorithm = Algorithm.HMAC256(JWT_TOKEN_SECRET);
          JWTVerifier jwtVerifier = JWT.require(algorithm).build();
          DecodedJWT decoded = jwtVerifier.verify(token);

          String username, email;
          Long id;
          username = decoded.getClaim("username").toString().split("\"")[1];
          email = decoded.getClaim("email").toString().split("\"")[1];
          id = decoded.getClaim("id").asLong();

          User user = authService.validUser(id, username, email);
          if (user != null) {
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
            authorities.add(new SimpleGrantedAuthority(user.getRole().getName()));

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
                null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            request.setAttribute("userId", user.getId());
            filterChain.doFilter(request, response);
          } else {
            filterChain.doFilter(request, response);
          }
        } catch (SignatureVerificationException sve) {
          try {
            sve.printStackTrace();
            response.setStatus(401);
            new ObjectMapper().writeValue(response.getWriter(), new Message(sve.getMessage()));
          } catch (IOException e) {
            e.printStackTrace();
          }
        } catch (TokenExpiredException exc) {
          try {
            exc.printStackTrace();
            response.setStatus(401);
            new ObjectMapper().writeValue(response.getWriter(),
                new Message(exc.getMessage()));
          } catch (IOException e) {
            e.printStackTrace();
          }
        } catch (Exception exc) {
          try {
            exc.printStackTrace();
            response.setStatus(400);
            new ObjectMapper().writeValue(response.getWriter(),
                new Message("Something went wrong during the request!"));
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      } else {
        filterChain.doFilter(request, response);
      }
    }
  }
}
