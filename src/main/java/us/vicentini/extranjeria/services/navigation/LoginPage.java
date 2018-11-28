package us.vicentini.extranjeria.services.navigation;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
class LoginPage extends BasePageNavigation {

    LoginPage(HtmlPage htmlPage, WebClient webClient) {
        super(htmlPage, webClient);
    }


    @Override
    protected void navigateImpl() throws IOException {
        log.info("Start Login");
        HtmlTextInput username = htmlPage.getElementByName("j_username");
        HtmlPasswordInput password = htmlPage.getElementByName("j_password");
        HtmlSubmitInput submit = htmlPage.getElementByName("Ingresar");
        username.setText("");
        password.setText("");
        htmlPage = submit.click();
        HtmlSpan myName = (HtmlSpan) htmlPage.getElementById("spanDetalle");
        log.info("logged: " + myName.asText());
        log.info("Finish Login");
    }


    @Override
    public PageNavigationStrategy next() {
        return new BienvenidoPage(htmlPage, webClient);
    }

}
