package ch15.htmlunit;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.gargoylesoftware.htmlunit.CollectingAlertHandler;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.MockWebConnection;
import com.gargoylesoftware.htmlunit.WebAssert;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class WindowConfirmTest extends ManagedWebClient {

  @Test
  public void testConfirmAlert()
      throws FailingHttpStatusCodeException, IOException {
    String title = "계약서";
    String confirmMsg = "상기 내용을 확인합니다.";
    String html = "<html><head><title>" + title + "</title>"
        + "<script>function go() { alert(confirm('" + confirmMsg
        + "'))}</script>\n</head><body onload='go()'></body></html>";
    
    URL testUrl = new URL("http://Page1/");
    MockWebConnection connection = new MockWebConnection();
    final List<String> confirmList = new ArrayList<String>();
    webClient.setAlertHandler(new CollectingAlertHandler());
    webClient.setConfirmHandler((page, msg)->{
      confirmList.add(msg);
      return true;
    });
    connection.setResponse(testUrl, html.getBytes(UTF_8), 200, "OK",
        "text/html; charset=UTF-8", null);
    webClient.setWebConnection(connection);
    HtmlPage firstPage = webClient.getPage(testUrl);
    WebAssert.assertTitleEquals(firstPage, title);
    assertArrayEquals(new String[] { confirmMsg }, confirmList.toArray());
    assertArrayEquals(new String[] { "true" },
        ((CollectingAlertHandler) webClient.getAlertHandler())
            .getCollectedAlerts().toArray());
  }

  @Test
  public void testConfirm() throws FailingHttpStatusCodeException, IOException {
    String title = "계약서";
    String confirmMsg = "상기 내용을 확인합니다.";
    String html = "<html><head><title>" + title + "</title></head>"
        + "<body onload='confirm(\"" + confirmMsg + "\")'></body></html>";
    URL testUrl = new URL("http://Page1/");
    MockWebConnection connection = new MockWebConnection();
    final List<String> confirmList = new ArrayList<String>();

    webClient.setConfirmHandler((page, msg) -> {
      confirmList.add(msg);
      return true;
    });
    connection.setResponse(testUrl, html.getBytes(UTF_8), 200, "OK",
        "text/html; charset=UTF-8", null);
    webClient.setWebConnection(connection);

    HtmlPage firstPage = webClient.getPage(testUrl);
    WebAssert.assertTitleEquals(firstPage, title);
    assertArrayEquals(new String[] { confirmMsg }, confirmList.toArray());
  }
}
