package hu.marko.szakdolgozat.spring.repository.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(columnDefinition = "int(11)")
  private Long id;
  @NotBlank
  @Column(nullable = false)
  private String name;
  @NotBlank
  @Column(columnDefinition = "date")
  private String birthdate;
  @NotBlank
  @Column(unique = true, nullable = false)
  private String username;
  @NotBlank
  @Email
  @Column(unique = true, nullable = false)
  private String email;
  @NotBlank
  @Column(nullable = false)
  private String password;
  @Column(columnDefinition = "boolean default true")
  private Boolean active;
  @CreationTimestamp
  @Column(updatable = false, columnDefinition = "datetime(6) default CURRENT_TIMESTAMP(6)")
  private Timestamp created;
  @ManyToOne
  @JoinColumn(name = "f_role_id", nullable = false)
  private Role role;
}
