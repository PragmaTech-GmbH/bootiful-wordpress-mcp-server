package digital.pragmatech.wordpress;

import org.springframework.boot.SpringApplication;

public class TestBootifulWordpressMcpServerApplication {

	public static void main(String[] args) {
		SpringApplication.from(Application::main).with(TestcontainersConfiguration.class).run(args);
	}

}
