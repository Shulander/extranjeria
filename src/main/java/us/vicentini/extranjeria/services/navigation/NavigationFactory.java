package us.vicentini.extranjeria.services.navigation;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NavigationFactory {

    @Setter(onMethod_ = {@Value("${login.username}")})
    private String username;

    @Setter(onMethod_ = {@Value("${login.password}")})
    private String password;

    public PageNavigationStrategy getStartPage(HtmlPage htmlPage, WebClient webClient) {
        return new StartPage(htmlPage, webClient, this);
    }

    PageNavigationStrategy getLoginPage(HtmlPage htmlPage, WebClient webClient) {
        LoginPage loginPage = new LoginPage(htmlPage, webClient, this);
        loginPage.setUsername(username);
        loginPage.setPassword(password);
        return loginPage;
    }

    PageNavigationStrategy getBienvenidoPage(HtmlPage htmlPage, WebClient webClient) {
        return new BienvenidoPage(htmlPage, webClient, this);
    }

    PageNavigationStrategy getSeleccionarTramitePage(HtmlPage htmlPage, WebClient webClient) {
        return new SeleccionarTramitePage(htmlPage, webClient, this);
    }

    PageNavigationStrategy getAntecedentesGeneralesPage(HtmlPage htmlPage, WebClient webClient) {
        return new AntecedentesGeneralesPage(htmlPage, webClient, this);
    }

    PageNavigationStrategy getRequisitosBasicosPage(HtmlPage htmlPage, WebClient webClient) {
        return new RequisitosBasicosPage(htmlPage, webClient, this);
    }

    PageNavigationStrategy getDatosUsuarioPage(HtmlPage htmlPage, WebClient webClient) {
        return new DatosUsuarioPage(htmlPage, webClient, this);
    }

    PageNavigationStrategy getReservaEnCalendarioPage(HtmlPage htmlPage, WebClient webClient) {
        return new ReservaEnCalendarioPage(htmlPage, webClient, this);
    }
}
