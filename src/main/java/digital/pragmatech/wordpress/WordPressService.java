package digital.pragmatech.wordpress;

import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

@Service
public class WordPressService {

  private final WordPressClient wordPressClient;

  public WordPressService(WordPressClient wordPressClient) {
    this.wordPressClient = wordPressClient;
  }

  @Tool(name = "wp_get_posts", description = "Get a list of WordPress posts")
  public List<Post> getPosts() {
    return wordPressClient.getPosts();
  }

  @Tool(name = "wp_create_draft", description = "Create a new WordPress post as draft")
  public Map<String, Object> createDraft(
    @Parameter(name = "title", description = "The title of the post") String title,
    @Parameter(name = "content", description = "The content of the post") String content
  ) {
    Post draft = new Post(title, content, "draft");
    Post createdPost = wordPressClient.createPost(draft);

    return Map.of(
      "id", createdPost.id(),
      "title", createdPost.title(),
      "content", createdPost.content(),
      "status", createdPost.status(),
      "link", createdPost.link() != null ? createdPost.link() : ""
    );
  }
}
