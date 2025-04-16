package digital.pragmatech.wordpress;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class WordPressClient {

  private static final Logger log = LoggerFactory.getLogger(WordPressClient.class);

  private final RestClient restClient;

  public WordPressClient(WordPressConfigurationProperties config) {

    RestClient.Builder builder = RestClient.builder()
      .baseUrl(config.getUrl());

    String auth = config.getUsername() + ":" + config.getPassword();
    String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
    builder.defaultHeader(HttpHeaders.AUTHORIZATION, "Basic " + encodedAuth);

    this.restClient = builder
      .defaultStatusHandler(HttpStatusCode::isError, (request, response) -> {
        String errorBody = new String(response.getBody().readAllBytes());
        log.error("WordPress API error: {} - {}", response.getStatusCode(), errorBody);
        throw new WordPressApiException(
          "WordPress API error: " + response.getStatusCode() + " - " + errorBody,
          response.getStatusCode().value()
        );
      })
      .build();
  }

  public List<Post> getPosts() {
    try {
      log.info("Fetching posts from WordPress API");
      Post[] posts = restClient.get()
        .uri("/posts")
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .body(Post[].class);

      if (posts == null) {
        log.warn("No posts returned from WordPress API");
        return List.of();
      }

      log.info("Successfully fetched {} posts", posts.length);
      return Arrays.asList(posts);
    } catch (Exception e) {
      if (!(e instanceof WordPressApiException)) {
        log.error("Error fetching posts", e);
        throw new WordPressApiException("Error fetching posts: " + e.getMessage(), e);
      }
      throw e;
    }
  }

  public Post createPost(Post post) {
    try {
      log.info("Creating new post with title: {}", post.title());

      Post createdPost = restClient.post()
        .uri("/posts")
        .contentType(MediaType.APPLICATION_JSON)
        .body(post)
        .retrieve()
        .body(Post.class);

      log.info("Successfully created post with ID: {}", createdPost.id());

      return createdPost;
    } catch (Exception e) {
      if (!(e instanceof WordPressApiException)) {
        log.error("Error creating post", e);
        throw new WordPressApiException("Error creating post: " + e.getMessage(), e);
      }
      throw e;
    }
  }
}
