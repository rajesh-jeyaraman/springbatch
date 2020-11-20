package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.integration.config.annotation.EnableBatchIntegration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;



@EnableBatchProcessing
@EnableBatchIntegration
@SpringBootApplication
//@RestController
public class DemoApplication {
	
	@Autowired
	private Job job;

	public static void main(String[] args) {
		
		 List<String> string = Arrays.asList(args);
	        List<String> finalArgs = new ArrayList<>(string.size() + 1);
	        finalArgs.addAll(string);
	        //finalArgs.add("inputFiles=transactionFile*.csv");
	        SpringApplication.run(DemoApplication.class, finalArgs.toArray(new String[finalArgs.size()]));

		//SpringApplication.run(DemoApplication.class, args);
	}

//	@GetMapping("/hello")
//	public String hello(@RequestParam(value = "name", defaultValue = "World") String name){
//		return String.format("Hello %s!", name);
//	}

}
