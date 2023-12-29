package ch15.htmlunit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.gargoylesoftware.htmlunit.html.HtmlListItem;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class HtmlUnitHomeTest extends ManagedWebClient {

  @Test
  public void homePage() throws IOException {
    HtmlPage page = webClient.getPage("http://htmlunit.sourceforge.net");
    assertEquals("HtmlUnit – Welcome to HtmlUnit", page.getTitleText());

    String pageAsXml = page.asXml();
    assertTrue(pageAsXml.contains("<div class=\"container-fluid\">"));

    String pageAsText = page.asNormalizedText();
    assertTrue(pageAsText.contains("Support for the HTTP and HTTPS protocols"));
  }

  @Test
  public void testClassNav() throws IOException {
    var page = "http://htmlunit.sourceforge.net/apidocs/index.html";
    HtmlPage mainPage = webClient.getPage(page);
    HtmlPage packagePage = (HtmlPage) mainPage.getFrameByName("packageFrame")
        .getEnclosedPage();
    HtmlListItem htmlListItem = (HtmlListItem) packagePage
        .getElementsByTagName("li").item(0);
    assertEquals("AboutURLConnection", htmlListItem.getTextContent());
  }
}
