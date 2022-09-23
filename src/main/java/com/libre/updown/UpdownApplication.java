package com.libre.updown;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.libre.updown.service.FileStorageService;

@SpringBootApplication
public class UpdownApplication implements CommandLineRunner {
	
	@Resource
	FileStorageService storageService;

	public static void main(String[] args) {
		SpringApplication.run(UpdownApplication.class, args);
	}
	
	@Override
	public void run(String... arg) throws Exception {
	  storageService.deleteAll();
	  storageService.init();
	}

}
