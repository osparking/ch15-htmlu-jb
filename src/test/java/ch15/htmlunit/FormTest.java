package ch15.htmlunit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gargoylesoftware.htmlunit.CollectingAlertHandler;
import com.gargoylesoftware.htmlunit.WebAssert;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class FormTest extends ManagedWebClient {
  
  @Test
  @DisplayName("입력 값 제공 때 경보 없음")
  public void testNoAlert() throws IOException {
    CollectingAlertHandler handler = new CollectingAlertHandler();
    webClient.setAlertHandler(handler);
    
    HtmlPage page = webClient.getPage("file:src/main/webapp/formtest.html");
    HtmlForm form = page.getFormByName("validated_form");
    HtmlTextInput input = form.getInputByName("in_text");
    input.setValueAttribute("입력...");
    HtmlSubmitInput submit = (HtmlSubmitInput) form.getInputByName("submit");
    HtmlPage resultPage = submit.click();
    WebAssert.assertTitleEquals(resultPage, "결과");
    assertTrue(handler.getCollectedAlerts().isEmpty(), "예상 밖 경보 발생!");
  }

  @Test
  public void testAlert() throws IOException {
    CollectingAlertHandler handler = new CollectingAlertHandler();
    webClient.setAlertHandler(handler);
    HtmlPage page = webClient.getPage("file:src/main/webapp/formtest.html");
    HtmlForm form = page.getFormByName("validated_form");
    HtmlSubmitInput button = form.getInputByName("submit");
    HtmlPage result = button.click();
    WebAssert.assertTitleEquals(result, page.getTitleText());
    WebAssert.assertTextPresent(result, page.asNormalizedText());
    
    List<String> collectedAlerts = handler.getCollectedAlerts();
    List<String> expectedAlerts = Collections.singletonList("값을 입력하세요.");
    assertEquals(expectedAlerts, collectedAlerts);
  }
  
  @Test
  public void testForm() throws IOException {
    HtmlPage page = webClient.getPage("file:src/main/webapp/formtest.html");
    HtmlForm form = page.getFormByName("validated_form");
    HtmlTextInput input = form.getInputByName("in_text");
    input.setValueAttribute("입력...");
    HtmlSubmitInput submit = (HtmlSubmitInput) form.getInputByName("submit");
    HtmlPage resultPage = submit.click();
    WebAssert.assertTitleEquals(resultPage, "결과");
  }
}
