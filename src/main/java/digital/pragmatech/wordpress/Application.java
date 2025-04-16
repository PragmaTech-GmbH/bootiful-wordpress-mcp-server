package digital.pragmatech.wordpress;

import java.util.List;

import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbacks;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public List<ToolCallback> wordpressTools(WordPressService wordPressService) {
		return List.of(ToolCallbacks.from(wordPressService));
	}
}
