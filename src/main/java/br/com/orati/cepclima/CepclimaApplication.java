package br.com.orati.cepclima;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CepclimaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CepclimaApplication.class, args);
	}

}
