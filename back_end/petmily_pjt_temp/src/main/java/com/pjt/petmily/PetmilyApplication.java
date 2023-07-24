package com.pjt.petmily;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PetmilyApplication {

	static {
		System.setProperty("com.amazonaws.sdk.disableEc2Metadata", "true");  // 추가
	}


	public static void main(String[] args) {
		SpringApplication.run(PetmilyApplication.class, args);
	}

}
