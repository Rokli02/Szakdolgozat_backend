package hu.marko.szakdolgozat.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// import org.springframework.context.annotation.Bean;
// import org.springframework.boot.CommandLineRunner;

// import hu.marko.szakdolgozat.spring.repository.RoleRepository;
// import hu.marko.szakdolgozat.spring.repository.model.Role;
// import hu.marko.szakdolgozat.spring.service.AuthorizationService;
// import hu.marko.szakdolgozat.spring.service.model.User;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	// @Bean
	// CommandLineRunner runner(AuthorizationService authorizationService,
	// RoleRepository roleRepository) {
	// return args -> {
	// roleRepository.save(new Role(1L, "user"));
	// roleRepository.save(new Role(2L, "siteManager"));
	// roleRepository.save(new Role(3L, "admin"));
	// };
	// }
}
