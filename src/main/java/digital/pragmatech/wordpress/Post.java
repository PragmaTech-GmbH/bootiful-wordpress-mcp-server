package digital.pragmatech.wordpress;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Post(
  Integer id,
  String title,
  String content,
  String excerpt,
  String status,
  @JsonProperty("date") LocalDateTime publishDate,
  @JsonProperty("modified") LocalDateTime modifiedDate,
  String slug,
  String link,
  @JsonProperty("featured_media") Integer featuredMedia
) {
  // Constructor for creating new posts (without ID and dates)
  public Post(String title, String content, String status) {
    this(null, title, content, null, status, null, null, null, null, null);
  }

  // Constructor for simple posts (for backward compatibility)
  public Post(String title, String content) {
    this(null, title, content, null, "draft", null, null, null, null, null);
  }
}
