package com.example.educationmanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(originPatterns = "*", allowCredentials = "true")
@SpringBootApplication
public class EducationManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(EducationManagementSystemApplication.class, args);
	}

}
