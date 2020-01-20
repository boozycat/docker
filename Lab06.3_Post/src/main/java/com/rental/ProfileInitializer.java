package com.rental;

import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.core.env.ConfigurableEnvironment;
//adding a comment here
public class ProfileInitializer
		implements ApplicationContextInitializer<AnnotationConfigServletWebServerApplicationContext> {
	public void initialize(AnnotationConfigServletWebServerApplicationContext context) {
	 	ConfigurableEnvironment appEnvironment = context.getEnvironment();
	 	appEnvironment.addActiveProfile(appEnvironment.getProperty("profile"));

	}
}
