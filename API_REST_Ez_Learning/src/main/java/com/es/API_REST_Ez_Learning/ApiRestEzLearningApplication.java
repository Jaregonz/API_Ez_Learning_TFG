package com.es.API_REST_Ez_Learning;

import com.es.API_REST_Ez_Learning.security.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class ApiRestEzLearningApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiRestEzLearningApplication.class, args);
	}

}
