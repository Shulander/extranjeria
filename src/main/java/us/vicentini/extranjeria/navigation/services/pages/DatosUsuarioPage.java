package us.vicentini.extranjeria.navigation.services.pages;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Slf4j
class DatosUsuarioPage extends BasePageNavigation {
    DatosUsuarioPage(HtmlPage htmlPage, WebClient webClient, NavigationFactory navigationFactory) {
        super(htmlPage, webClient, navigationFactory);
    }


    @Override
    protected void navigateImpl() throws IOException {
        log.info("Start Datos Usuario");

        List<HtmlButton> htmlButtons = Collections.emptyList();
        while (htmlButtons.isEmpty()) {
            log.debug("dialog button is null... waiting");
            webClient.waitForBackgroundJavaScript(60000);
            htmlButtons = htmlPage.getByXPath(
                    "//button[@class='ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only']");
        }
        HtmlButton noButton = getDialogNoButton(htmlButtons);
        htmlPage = noButton.click();

        HtmlButton nextPageButton = null;
        for (int i = 0; nextPageButton == null && i < 10; i++) {
            log.debug("next button is null... waiting");
            webClient.waitForBackgroundJavaScript(60000);
            nextPageButton = (HtmlButton) htmlPage.getElementById("btnSiguienteDU");
        }
        if (nextPageButton != null) {
            htmlPage = nextPageButton.click();
        }


        log.info("Finish Datos Usuario");
    }


    private HtmlButton getDialogNoButton(List<HtmlButton> htmlButtons) {
        for (HtmlButton button : htmlButtons) {
            HtmlSpan innerSpan = (HtmlSpan) button.getFirstChild();
            if (innerSpan.asText().equals("No")) {
                return button;
            }
        }
        return null;
    }


    @Override
    public PageNavigationStrategy next() {
        return navigationFactory.getReservaEnCalendarioPage(htmlPage, webClient);
    }
}
