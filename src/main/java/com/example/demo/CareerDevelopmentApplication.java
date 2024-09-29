package com.example.demo;

import com.example.demo.dtos.DepartmentsDTO;
import com.example.demo.dtos.TitlesDTO;
import com.example.demo.dtos.UsersDTO;
import com.example.demo.entities.Departments;
import com.example.demo.entities.Titles;
import com.example.demo.entities.Users;
import com.example.demo.services.DepartmentsServices;
import com.example.demo.services.TitlesServices;
import com.example.demo.services.UsersServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class CareerDevelopmentApplication {

	@Autowired
	private UsersServices usersServices;

	@Autowired
	private DepartmentsServices departmentsServices;

	@Autowired
	private TitlesServices titlesServices;



	public static void main(String[] args) {
		SpringApplication.run(CareerDevelopmentApplication.class, args);
	}


//	@Override
//	public void run(String... args) throws Exception {
//
////		UUID titleUuid = UUID.randomUUID();
////		Titles titles = new Titles(titleUuid, );
////		UUID userUuid = UUID.randomUUID();
////		UsersDTO usersDTO = new UsersDTO("Karim", "Ahmed", "kimo.abozeed@gmail.com", "123","01021343238", null, null);
//
//
////		usersServices.addUser(usersDTO);
////
////		UsersDTO usersDTO2 = new UsersDTO();
////		usersDTO2.setFirstName("Omar");
////
////		usersServices.updateUsers(UUID.fromString("6e4fe5f2-4c72-493e-b6da-1b2ed582dce2"), usersDTO2);
////
//
////		UsersDTO returnedDto = usersServices.getUser(UUID.fromString("6e4fe5f2-4c72-493e-b6da-1b2ed582dce2"));
////		System.out.println(returnedDto.toString());
////
////		usersServices.deleteUser(UUID.fromString("b05cf9d0-37d2-411e-a1e6-9421e42c1829"));
//
//
//		DepartmentsDTO departmentsDTO= new DepartmentsDTO("Software Engineering");
//		DepartmentsDTO departmentsDTO2 = new DepartmentsDTO("Quality Assurance");
//
//		departmentsServices.addDepartment(departmentsDTO);
//		departmentsServices.updateDepartment(UUID.fromString("6cfb159c-95b5-4d7f-b989-0dd9629ecdda"), departmentsDTO2);
//		System.out.println(departmentsServices.getDepartment(UUID.fromString("6cfb159c-95b5-4d7f-b989-0dd9629ecdda")));
//
//		TitlesDTO titlesDTO = new TitlesDTO("Associate Quality Assurance Engineer", false, UUID.fromString("6cfb159c-95b5-4d7f-b989-0dd9629ecdda"));
//		TitlesDTO titlesDTO2 = new TitlesDTO("Quality Assurance Engineer", null, null);
//
//
//		titlesServices.addTitles(titlesDTO);
//		titlesServices.addTitles(titlesDTO2);
////		titlesDTO.setTitle(null);
////		titlesServices.updateTitles(UUID.fromString("9e4c9f85-e85e-4d51-80ed-2a9669dd11a9"), titlesDTO);
////		System.out.println(titlesServices.getTitles(UUID.fromString("9e4c9f85-e85e-4d51-80ed-2a9669dd11a9")));
//
//	}
}

