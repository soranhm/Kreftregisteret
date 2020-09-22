package com.test.api;

import java.time.LocalDateTime;

import com.test.api.model.*;
import com.test.api.repository.*;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiApplication {

	public static void main(final String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(final UserRepository userRepository, final UnitRepository unitRepository,
			final RoleRepository roleRepository, final UserRoleRepository userRoleRepository) {
		return (args) -> {
			// save a couple of users
			userRepository.save(new User("Alice", 1));
			userRepository.save(new User("Bob", 2));
			userRepository.save(new User("Eve", 1));
			unitRepository.save(new Unit("Kreftregisteret", 2));
			unitRepository.save(new Unit("Akershus universitetssykehus HF", 1));
			unitRepository.save(new Unit("Sørlandet sykehus HF", 2));
			unitRepository.save(new Unit("Vestre Viken HF", 2));
			roleRepository.save(new Role("User administration", 1));
			roleRepository.save(new Role("Endoscopist administration", 2));
			roleRepository.save(new Role("Report colonoscopy capacity", 1));
			roleRepository.save(new Role("Send invitations", 2));
			roleRepository.save(new Role("View statistics", 2));
			userRoleRepository.save(new UserRole(1, 1, 11, 101, LocalDateTime.of(2019, 01, 02, 00, 00, 00, 00), LocalDateTime.of(2019, 12, 13, 23, 59, 59, 00)));
			userRoleRepository.save(new UserRole(2, 1, 11, 104, LocalDateTime.of(2019, 01, 02, 00, 00, 00, 00), LocalDateTime.of(2019, 12, 13, 23, 59, 59, 00)));
			userRoleRepository.save(new UserRole(1, 1, 11, 105, LocalDateTime.of(2019, 06, 11, 00, 00, 00, 00), null));
			userRoleRepository.save(new UserRole(2, 2, 12, 101, LocalDateTime.of(2019, 01, 28, 00, 00, 00, 00), null));
			userRoleRepository.save(new UserRole(1, 2, 12, 105, LocalDateTime.of(2019, 01, 28, 00, 00, 00, 00), null));
			userRoleRepository.save(new UserRole(1, 2, 14, 101, LocalDateTime.of(2019, 01, 28, 00, 00, 00, 00), null));
			userRoleRepository.save(new UserRole(1, 2, 14, 102, LocalDateTime.of(2019, 01, 28, 00, 00, 00, 00), null));
			userRoleRepository.save(new UserRole(1, 1, 11, 101, LocalDateTime.of(2019, 02, 01, 07, 00, 00, 00), null));
			userRoleRepository.save(new UserRole(1, 1, 11, 104, LocalDateTime.of(2019, 02, 01, 07, 00, 00, 00), null));
		};
	}
}
