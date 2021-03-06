package us.vicentini.extranjeria.navigation.services.pages;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
class AntecedentesGeneralesPage extends BasePageNavigation {
    AntecedentesGeneralesPage(HtmlPage htmlPage, WebClient webClient, NavigationFactory navigationFactory) {
        super(htmlPage, webClient, navigationFactory);
    }


    @Override
    protected void navigateImpl() throws IOException {
        log.info("Start Antecedentes Generales");
        HtmlButton nextPageButton = null;
        while (nextPageButton == null) {
            log.debug("next button is null... waiting");
            webClient.waitForBackgroundJavaScript(60000);
            nextPageButton = (HtmlButton) htmlPage.getElementById("btnSiguienteAG");
        }
        htmlPage = nextPageButton.click();
        log.info("Finish Antecedentes Generales");
    }


    @Override
    public PageNavigationStrategy next() {
        return navigationFactory.getRequisitosBasicosPage(htmlPage, webClient);
    }
}
