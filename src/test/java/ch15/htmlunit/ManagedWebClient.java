package ch15.htmlunit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import com.gargoylesoftware.htmlunit.WebClient;

public abstract class ManagedWebClient {
  protected WebClient webClient;
  
  @BeforeEach
  public void setUp() {
    webClient = new WebClient();
  }
  
  @AfterEach
  public void tearDown() {
    webClient.close();
  }
}
