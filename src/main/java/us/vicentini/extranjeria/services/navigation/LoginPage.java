package us.vicentini.extranjeria.services.navigation;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
class LoginPage extends BasePageNavigation {

    @Setter
    private String username;
    @Setter
    private String password;

    LoginPage(HtmlPage htmlPage, WebClient webClient, NavigationFactory navigationFactory) {
        super(htmlPage, webClient, navigationFactory);
    }


    @Override
    protected void navigateImpl() throws IOException {
        log.info("Start Login");
        HtmlTextInput usernameInput = htmlPage.getElementByName("j_username");
        HtmlPasswordInput passwordInput = htmlPage.getElementByName("j_password");
        HtmlSubmitInput submit = htmlPage.getElementByName("Ingresar");
        usernameInput.setText(username);
        passwordInput.setText(password);
        htmlPage = submit.click();
        HtmlSpan myName = (HtmlSpan) htmlPage.getElementById("spanDetalle");
        log.info("logged: " + myName.asText());
        log.info("Finish Login");
    }


    @Override
    public PageNavigationStrategy next() {
        return navigationFactory.getBienvenidoPage(htmlPage, webClient);
    }

}
