package com.pjt.petmily;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class PetmilyApplication {

	static {
		System.setProperty("com.amazonaws.sdk.disableEc2Metadata", "true");  // 추가
	}
	@PostConstruct
	public void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
	}

	public static void main(String[] args) {
		SpringApplication.run(PetmilyApplication.class, args);
	}
}
