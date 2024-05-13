package com.ll.topcastingbe;

import jakarta.annotation.PostConstruct;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class TopCastingBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TopCastingBeApplication.class, args);
	}

	@Component
	public static class ProfileLogger {
		@Autowired
		private Environment env;

		private static final Logger logger = LoggerFactory.getLogger(ProfileLogger.class);

		@PostConstruct
		public void logProfile() {
			logger.info("Active profiles: {}", Arrays.toString(env.getActiveProfiles()));

			// Google OAuth2 Redirect URI 설정 값 로깅
			String googleRedirectUri = env.getProperty("spring.security.oauth2.client.registration.google.redirect-uri");
			logger.info("Google Redirect URI: {}", googleRedirectUri);
		}
	}

}
