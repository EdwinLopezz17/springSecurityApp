package com.app.SpringSecurityApp;

import com.app.SpringSecurityApp.domain.entities.PermissionEntity;
import com.app.SpringSecurityApp.domain.entities.RoleEntity;
import com.app.SpringSecurityApp.domain.entities.UserEntity;
import com.app.SpringSecurityApp.domain.models.PermissionEnum;
import com.app.SpringSecurityApp.domain.models.RoleEnum;
import com.app.SpringSecurityApp.infrastructure.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Set;

@SpringBootApplication
public class SpringSecurityAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityAppApplication.class, args);
	}


	@Bean
	CommandLineRunner commandLineRunner(UserRepository userRepository) {

		return args -> {
			PermissionEntity createPermission = PermissionEntity.builder()
					.permission(PermissionEnum.CREATE)
					.build();
			PermissionEntity readPermission = PermissionEntity.builder()
					.permission(PermissionEnum.READ)
					.build();
			PermissionEntity updatePermission = PermissionEntity.builder()
					.permission(PermissionEnum.UPDATE)
					.build();
			PermissionEntity deletePermission = PermissionEntity.builder()
					.permission(PermissionEnum.DELETE)
					.build();


			RoleEntity roleAdmin = RoleEntity.builder()
					.role(RoleEnum.ADMIN)
					.permissions(Set.of(createPermission, readPermission, updatePermission, deletePermission))
					.build();

			RoleEntity roleDeb = RoleEntity.builder()
					.role(RoleEnum.DEVELOPER)
					.permissions(Set.of(createPermission, readPermission, updatePermission, deletePermission))
					.build();

			RoleEntity roleUser = RoleEntity.builder()
					.role(RoleEnum.USER)
					.permissions(Set.of(createPermission, readPermission, updatePermission))
					.build();

			RoleEntity roleInvited = RoleEntity.builder()
					.role(RoleEnum.INVITED)
					.permissions(Set.of(readPermission))
					.build();


			UserEntity userEdwin = UserEntity.builder()
					.username("edwinadmin")
					.password("$2a$10$kWhiin/NSHn85pLM3wg0qeCEFkymFDKuLebZ.vna2cYn2cJPQWreq")
					.accountNonExpired(true)
					.accountNonLocked(true)
					.credentialsNonExpired(true)
					.enabled(true)
					.roles(Set.of(roleAdmin))
					.build();

			UserEntity userZepol = UserEntity.builder()
					.username("zepoldev")
					.password("$2a$10$kWhiin/NSHn85pLM3wg0qeCEFkymFDKuLebZ.vna2cYn2cJPQWreq")
					.accountNonExpired(true)
					.accountNonLocked(true)
					.credentialsNonExpired(true)
					.enabled(true)
					.roles(Set.of(roleAdmin, roleDeb))
					.build();

			UserEntity userInvit = UserEntity.builder()
					.username("invitado")
					.password("$2a$10$kWhiin/NSHn85pLM3wg0qeCEFkymFDKuLebZ.vna2cYn2cJPQWreq")
					.accountNonExpired(true)
					.accountNonLocked(true)
					.credentialsNonExpired(true)
					.enabled(true)
					.roles(Set.of(roleInvited))
					.build();

			userRepository.saveAll(List.of(userEdwin, userZepol, userInvit));
		};
	}
}
