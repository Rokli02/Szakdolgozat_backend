package hu.marko.szakdolgozat.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// import org.springframework.context.annotation.Bean;
// import org.springframework.boot.CommandLineRunner;
// import hu.marko.szakdolgozat.spring.repository.RoleRepository;
// import hu.marko.szakdolgozat.spring.repository.model.Role;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	// @Bean
	// CommandLineRunner runner(RoleRepository roleRepository) {
	// return args -> {
	// roleRepository.save(new Role(null, "user"));
	// roleRepository.save(new Role(null, "siteManager"));
	// roleRepository.save(new Role(null, "admin"));
	// };
	// }
}
