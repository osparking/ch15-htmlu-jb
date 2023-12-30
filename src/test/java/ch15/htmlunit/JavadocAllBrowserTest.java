package ch15.htmlunit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebAssert;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlListItem;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class JavadocAllBrowserTest {

  private static Collection<BrowserVersion[]> getBrowserVersions() {
    return Arrays.asList(new BrowserVersion[][] { { BrowserVersion.FIREFOX },
        { BrowserVersion.INTERNET_EXPLORER }, { BrowserVersion.CHROME },
        { BrowserVersion.BEST_SUPPORTED } });
  }

  @ParameterizedTest
  @MethodSource("getBrowserVersions")
  public void testClassNav(BrowserVersion browserVersion) {
    try (WebClient webClient = new WebClient(browserVersion)) {
      String docsPage = "http://htmlunit.sourceforge.net/apidocs/index.html";
      HtmlPage mainPage = (HtmlPage) webClient.getPage(docsPage);

      WebAssert.notNull("메인 페이지가 없음", mainPage);

      HtmlPage packagePage = (HtmlPage) mainPage.getFrameByName("packageFrame")
          .getEnclosedPage();
      WebAssert.notNull("패키지 틀이 없음", packagePage);

      HtmlListItem htmlListItem = (HtmlListItem) packagePage
          .getElementsByTagName("li").item(0);
      assertEquals("AboutURLConnection", htmlListItem.getTextContent());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
