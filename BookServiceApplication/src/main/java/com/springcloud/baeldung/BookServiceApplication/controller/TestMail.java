package com.springcloud.baeldung.BookServiceApplication.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller("/client")
public class TestMail {

	RestTemplate restTemplate = new RestTemplate();
	final String baseUrl = "http://email/sendemail";
	final String message = "Succesfully sent mail from email microservice";

	@GetMapping("/sendmail")
	@ResponseBody
	public ResponseEntity<String> sendmail() {
		ResponseEntity<String> result = restTemplate.postForEntity(baseUrl, message, String.class);
		return result;
	}
	
    
}
