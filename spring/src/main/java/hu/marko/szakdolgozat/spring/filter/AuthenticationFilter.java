package hu.marko.szakdolgozat.spring.filter;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import hu.marko.szakdolgozat.spring.controller.model.Message;
import hu.marko.szakdolgozat.spring.controller.model.auth.LoginData;
import hu.marko.szakdolgozat.spring.exception.BadRequestException;
import hu.marko.szakdolgozat.spring.exception.NotFoundException;
import hu.marko.szakdolgozat.spring.filter.model.UserDetailsModel;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
  private final AuthenticationManager authenticationManager;
  private final String JWT_TOKEN_SECRET = "secrEtOfJWTokN_123456";

  public AuthenticationFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
    super.setFilterProcessesUrl("/api/auth/login");
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
      throws AuthenticationException {

    String username = "", password = "";
    try {
      List<String> randomList = request.getReader().lines().collect(Collectors.toList());
      if (randomList.size() > 1) {
        for (String random : randomList) {
          String line = random;
          if (line.contains("usernameOrEmail")) {
            username = getValueFromJsonKeyValuePair(line);
            continue;
          }
          if (line.contains("password")) {
            password = getValueFromJsonKeyValuePair(line);
            break;
          }
        }
      } else {
        String[] keyValuePairs = randomList.get(0).split(",");
        for (String pair : keyValuePairs) {
          if (pair.contains("usernameOrEmail")) {
            username = getValueFromJsonKeyValuePair(pair);
            continue;
          }
          if (pair.contains("password")) {
            password = getValueFromJsonKeyValuePair(pair);
            break;
          }
        }
      }

    } catch (IOException e1) {
      e1.printStackTrace();
    }

    if (username == "" || password == "") {
      try {
        response.setStatus(400);
        new ObjectMapper().writeValue(response.getWriter(),
            new Message("Username or email, and password must be given!"));
      } catch (IOException e) {
        e.printStackTrace();
      }
      return null;
    }

    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
        password);
    try {
      return authenticationManager.authenticate(authenticationToken);
    } catch (NotFoundException exc) {
      try {
        response.setStatus(404);
        new ObjectMapper().writeValue(response.getWriter(), new Message(exc.getMessage()));
      } catch (IOException e) {
        e.printStackTrace();
      }
    } catch (BadRequestException exc) {
      try {
        response.setStatus(400);
        new ObjectMapper().writeValue(response.getWriter(), new Message(exc.getMessage()));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
      Authentication authResult) throws IOException, ServletException {

    UserDetailsModel user = (UserDetailsModel) authResult.getPrincipal();
    Algorithm algorithm = Algorithm.HMAC256(JWT_TOKEN_SECRET);

    String token = JWT.create()
        .withClaim("id", user.getId())
        .withClaim("username", user.getUsername())
        .withClaim("email", user.getLoginUser().getEmail())
        .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
        .sign(algorithm);

    LoginData loginData = new LoginData(token, user.getLoginUser());
    response.setHeader("token", "Bearer " + token);
    new ObjectMapper().writeValue(response.getWriter(), loginData);
  }

  private String getValueFromJsonKeyValuePair(String line) {
    return line.split(":")[1].split("\"")[1];
  }
}
