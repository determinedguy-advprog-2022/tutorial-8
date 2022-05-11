package id.ac.ui.cs.advprog.asdos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AsdosApplication {
	public static void main(String[] args) {
		SpringApplication.run(AsdosApplication.class, args);
	}

}
