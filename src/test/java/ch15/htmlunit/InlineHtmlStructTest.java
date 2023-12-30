package ch15.htmlunit;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.net.URL;

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

  @Test
  public void testInLineHtmlStructures() throws IOException {
    final URL pageUrl1 = new URL("http://url1/");
    final URL pageUrl2 = new URL("http://url2/");
    final URL pageUrl3 = new URL("http://url3/");

    MockWebConnection connection = new MockWebConnection();
    String html1 = "<html><head><title>제목1</title></head></html>";
    String html2 = "<html><head><title>제목2</title></head></html>";
    String html3 = "<html><head><title>제목3</title></head></html>";

    connection.setResponse(pageUrl1, html1.getBytes(UTF_8), 200, "OK",
        "text/html; charset=UTF-8", null);
    connection.setResponse(pageUrl2, html2.getBytes(UTF_8), 200, "OK",
        "text/html; charset=UTF-8", null);
    connection.setResponse(pageUrl3, html3.getBytes(UTF_8), 200, "OK",
        "text/html; charset=UTF-8", null);

    webClient.setWebConnection(connection);

    HtmlPage page1 = webClient.getPage(pageUrl1);
    WebAssert.assertTitleEquals(page1, "제목1");
    HtmlPage page2 = webClient.getPage(pageUrl2);
    WebAssert.assertTitleEquals(page2, "제목2");
    HtmlPage page3 = webClient.getPage(pageUrl3);
    WebAssert.assertTitleEquals(page3, "제목3");
  }
}
