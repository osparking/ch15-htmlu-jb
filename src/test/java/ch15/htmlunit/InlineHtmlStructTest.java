package ch15.htmlunit;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gargoylesoftware.htmlunit.MockWebConnection;
import com.gargoylesoftware.htmlunit.WebAssert;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class InlineHtmlStructTest extends ManagedWebClient {

  @Test
  @DisplayName("내포된 HTML 페이지 - UTF-8 내용")
  public void testInlineHtmlStructure() throws IOException {
    final String expectedTitle = "협회 홈페이지";
    String html = "<html><head><title>" + expectedTitle
        + "</title></head></html>";
    MockWebConnection connection = new MockWebConnection();
    connection.setDefaultResponse(html.getBytes(UTF_8), 200, "OK",
        "text/html; charset=UTF-8");
    webClient.setWebConnection(connection);
    HtmlPage page = webClient.getPage("http://page");
    WebAssert.assertTitleEquals(page, expectedTitle);
  }
}
