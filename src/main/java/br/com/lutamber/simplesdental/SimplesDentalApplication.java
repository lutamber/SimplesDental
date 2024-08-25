package br.com.lutamber.simplesdental;

import br.com.lutamber.simplesdental.repository.profissionais.ProfissionaisRepository;
import br.com.lutamber.simplesdental.service.profissionais.DefaultProfissionalService;
import br.com.lutamber.simplesdental.service.profissionais.ProfissionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SimplesDentalApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimplesDentalApplication.class, args);
	}

}
