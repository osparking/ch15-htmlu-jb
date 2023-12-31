package ch15.htmlunit;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.gargoylesoftware.htmlunit.WebAssert;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class FormTest extends ManagedWebClient {

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
