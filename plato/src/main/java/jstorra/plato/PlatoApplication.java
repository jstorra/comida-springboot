package jstorra.plato;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class PlatoApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext contexto = SpringApplication.run(PlatoApplication.class, args);
	}
}