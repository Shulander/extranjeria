package us.vicentini.extranjeria.navigation.services.pages;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlRadioButtonInput;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
class RequisitosBasicosPage extends BasePageNavigation {
    RequisitosBasicosPage(HtmlPage htmlPage, WebClient webClient, NavigationFactory navigationFactory) {
        super(htmlPage, webClient, navigationFactory);
    }


    @Override
    protected void navigateImpl() throws IOException {
        log.info("Start Requisitos Basicos");

        HtmlRadioButtonInput opcionRadio1 = null;
        while (opcionRadio1 == null) {
            log.debug("radio button option is null... waiting");
            webClient.waitForBackgroundJavaScript(60000);
            opcionRadio1 = (HtmlRadioButtonInput) htmlPage.getElementById("opcionRadio1");
        }
        htmlPage = (HtmlPage) opcionRadio1.setChecked(true);

        HtmlButton nextPageButton = (HtmlButton) htmlPage.getElementById("btnSiguienteRB");
        htmlPage = nextPageButton.click();

        log.info("Finish Requisitos Basicos");
    }


    @Override
    public PageNavigationStrategy next() {
        return navigationFactory.getDatosUsuarioPage(htmlPage, webClient);
    }
}
