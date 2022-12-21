package com.project.BankApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.project.BankApplication.repo.AccountRepository;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Rest API", version = "2.0", description = "Bank Account information"))
public class BankApplication {

	@Autowired
	AccountRepository accRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(BankApplication.class, args);
	}

}
