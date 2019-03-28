package com.springcloud.baeldung.EmailApplication.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Bean
	public JavaMailSenderImpl mailSender() {

		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		ClassLoader classLoader = getClass().getClassLoader();
		try {
			File file = new File(classLoader.getResource("mail.properties").getFile());
			FileInputStream fileInput = new FileInputStream(file);
			mailSender.setHost("smtp.gmail.com");
			mailSender.setPort(587);
			// TODO: authentication not working - need to check.
			// mailSender.setUsername("noreply-daac.india@siemens.com");
			// mailSender.setPassword("Welcome@123$");
			mailSender.setDefaultEncoding("UTF-8");
			Properties props = mailSender.getJavaMailProperties();
			props.load(fileInput);
		} catch (FileNotFoundException exception) {
			exception.printStackTrace();

		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
		return mailSender;
	}
	
	@Bean
	public TaskExecutor threadPoolTaskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(4);
		executor.setMaxPoolSize(4);
		executor.setThreadNamePrefix("default_task_executor_thread");
		executor.initialize();
		return executor;
	}
}
