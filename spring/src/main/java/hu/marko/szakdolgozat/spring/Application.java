package hu.marko.szakdolgozat.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// import org.springframework.context.annotation.Bean;
// import org.springframework.boot.CommandLineRunner;
// import hu.marko.szakdolgozat.spring.service.AuthorizationService;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	// @Bean
	// CommandLineRunner runner(AuthorizationService authorizationService) {
	// return args -> {
	// authorizationService
	// .signup(new User(null, "Kerék Béla", "kbla", "kbla@username.com",
	// "2000-10-19", "123", null, null, null));
	// authorizationService.signup(
	// new User(null, "Keréktelen Áron", "aronka", "kl-aron@username.go",
	// "1986-06-22", "123", null, null, null));
	// };
	// }
}
