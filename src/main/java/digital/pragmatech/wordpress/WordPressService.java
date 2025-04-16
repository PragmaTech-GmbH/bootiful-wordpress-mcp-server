package digital.pragmatech.wordpress;

import java.util.List;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

@Service
public class WordPressService {

  @Tool(name = "wp_get_posts", description = "Get a list of WordPress posts")
  public List<Post> getPosts() {
    return List.of(
      new Post("Debugging in Java", "Java debugging tips"),
      new Post("Spring Boot", "Spring Boot is a framework for Java")
    );
  }
}
