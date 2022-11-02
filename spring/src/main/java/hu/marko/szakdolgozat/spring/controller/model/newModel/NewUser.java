package hu.marko.szakdolgozat.spring.controller.model.newModel;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewUser {
  @NotBlank
  private String name;
  @NotBlank
  private String username;
  @NotBlank
  @Email
  private String email;
  @NotBlank
  @DateTimeFormat
  private String birthdate;
  @NotBlank
  private String password;
}
