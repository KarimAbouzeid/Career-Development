package com.example.demo;

import com.example.demo.dtos.UsersDTO;
import com.example.demo.entities.Titles;
import com.example.demo.entities.Users;
import com.example.demo.services.UsersServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class CareerDevelopmentApplication implements CommandLineRunner {

	@Autowired
	private UsersServices usersServices;



	public static void main(String[] args) {
		SpringApplication.run(CareerDevelopmentApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {

//		UUID titleUuid = UUID.randomUUID();
//		Titles titles = new Titles(titleUuid, );
		UUID userUuid = UUID.randomUUID();
		UsersDTO usersDTO = new UsersDTO("Karim", "Ahmed", "kimo.abozeed@gmail.com", "123","01021343238", null, null);

		usersServices.addUser(usersDTO);

//		UsersDTO usersDTO2 = new UsersDTO();
//		usersDTO2.setFirstName("Omar");

//		usersServices.updateUsers(UUID.fromString("6e4fe5f2-4c72-493e-b6da-1b2ed582dce2"), usersDTO2);

//		UsersDTO returnedDto = usersServices.getUser(UUID.fromString("6e4fe5f2-4c72-493e-b6da-1b2ed582dce2"));
//		System.out.println(returnedDto.toString());
//
//		usersServices.deleteUser(UUID.fromString("b05cf9d0-37d2-411e-a1e6-9421e42c1829"));



	}
}
