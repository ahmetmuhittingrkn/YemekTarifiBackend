package com.example.yemek_tarifi;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class YemekTarifiApplication {

	public static void main(String[] args) {
		SpringApplication.run(YemekTarifiApplication.class, args);
	}

	@Bean // Bu anotasyon, metodun döndürdüğü nesnenin bir Spring Bean'i olduğunu belirtir.
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
