package rest_demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import rest_demo.service.EmployeeService;

@SpringBootApplication
public class SpringRestStarter implements CommandLineRunner {

	@Autowired
	EmployeeService employeeService;

	public static void main(String[] args) {
		SpringApplication.run(SpringRestStarter.class, args);
	}

	@Override
	public void run(String... args) {
		// employeeService.getEmployeeById(1);
		// employeeService.getEmployeeByAge(60);
	}
}
