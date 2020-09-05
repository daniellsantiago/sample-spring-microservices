package daniellsantiago.study.authservice;

import daniellsantaigo.study.commonclasses.model.ApplicationUser;
import daniellsantaigo.study.commonclasses.property.JwtConfiguration;
import daniellsantaigo.study.commonclasses.repository.ApplicationUserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class AuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}

	@Bean
	public ApplicationUserRepository applicationUserRepository() {
		ApplicationUserRepository repository = new ApplicationUserRepository();
		repository.add(new ApplicationUser(1L, "daniel", "$2a$10$ZpfnI6lpx5CCCySA0BQbXelW18DWV87C2NIIEUTFXSv2CFvTcRAQe", "USER"));
		return repository;
	}

	@Bean
	public JwtConfiguration jwtConfiguration() {
		return new JwtConfiguration();
	}
}
