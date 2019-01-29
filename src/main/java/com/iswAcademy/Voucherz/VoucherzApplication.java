package com.iswAcademy.Voucherz;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import javax.annotation.PostConstruct;
import java.util.TimeZone;


@SpringBootApplication
@EnableDiscoveryClient
public class VoucherzApplication implements CommandLineRunner {



	@PostConstruct
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

	}


	public static void main(String[] args) {
		SpringApplication.run(VoucherzApplication.class, args);

	}


	@Override
	public void run(String... args) throws Exception {

	}
}

