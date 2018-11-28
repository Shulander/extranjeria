package us.vicentini.extranjeria.services.navigation;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
class BienvenidoPage extends BasePageNavigation {

    BienvenidoPage(HtmlPage htmlPage, WebClient webClient) {
        super(htmlPage, webClient);
    }


    @Override
    public void navigateImpl() throws IOException {
        log.info("Start Bienvenido");
        HtmlAnchor reservarHora = (HtmlAnchor) htmlPage.getElementById("btnReservarHora");
        htmlPage = reservarHora.click();
        log.info("Finish Bienvenido");
    }


    @Override
    public PageNavigationStrategy next() {
        return new SeleccionarTramitePage(htmlPage, webClient);
    }
}
