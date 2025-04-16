package digital.pragmatech.wordpress;

public class WordPressApiException extends RuntimeException {

  private final int statusCode;

  public WordPressApiException(String message, int statusCode) {
    super(message);
    this.statusCode = statusCode;
  }

  public WordPressApiException(String message, Throwable cause) {
    super(message, cause);
    this.statusCode = 0;
  }

  public int getStatusCode() {
    return statusCode;
  }
}
