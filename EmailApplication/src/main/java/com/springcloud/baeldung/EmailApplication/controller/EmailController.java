package com.springcloud.baeldung.EmailApplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springcloud.baeldung.EmailApplication.data.Email;
import com.springcloud.baeldung.EmailApplication.service.EmailService;


@Controller
public class EmailController {

	private static final String FROM = "aviralrocking@gmail.com";
	private static final String TO = "aviraljain0286@yahoo.com";
	
	@Autowired
	private EmailService emailService;

	@Autowired
	private TaskExecutor taskExecutor;
	
	@GetMapping("")
	@ResponseBody
	public String hello() {
		return "<div class=\\\"row\\\" align=\\\"center\\\"><h6>Waiting for Email...</h6></div>";
	}
	
	@PostMapping("/sendmail")
	@ResponseBody
	public String sendmail(@RequestBody String message, BindingResult result) {
		
		if(result.hasErrors()) {
			return "error";
		}
		
		taskExecutor.execute(new Runnable() {
			
			@Override
			public void run() {
				Email email = new Email(FROM, TO, "Test Mail", message);
				email.setIsHtml(false);
				emailService.send(email);
			}
		});
		return "<div class=\"row\" align=\"center\"><h1>Your request has been successfully submitted</h1></div>";
	}
}