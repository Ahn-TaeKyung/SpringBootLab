package com.rookies3.myspringbootlab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MySpringbootLabApplication {

	public static void main(String[] args) {
//		SpringApplication.run(MySpringBootAppApplication.class, args);

		SpringApplication application = new SpringApplication(MySpringbootLabApplication.class);
		//기본적으로 WebApplicationType 은 웹어플리케이션
		application.setWebApplicationType(WebApplicationType.SERVLET);
		application.run(args);

	}

}
