package us.vicentini.extranjeria.services.navigation;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
class ReservaEnCalendarioPage extends BasePageNavigation {
    ReservaEnCalendarioPage(HtmlPage htmlPage, WebClient webClient) {
        super(htmlPage, webClient);
    }


    @Override
    protected void navigateImpl() throws IOException {
        log.info("Start Reserva En Calendario");
        clickPreviousIfAvailable();
        do {
            checkAvailableTimes();
        } while (nextPage());
        log.info("Finish Reserva En Calendario");
    }


    private boolean nextPage() throws IOException {
        HtmlButton htmlButton = null;
        while (htmlButton == null) {
            log.debug("Next Button is null... waiting");
            webClient.waitForBackgroundJavaScript(60000);
            htmlButton = (HtmlButton) htmlPage.getElementById("btnNext");
        }
        if (!htmlButton.isDisabled()) {
            htmlPage = htmlButton.click();
            return true;
        }
        return false;
    }


    private void checkAvailableTimes() {
        List<HtmlDivision> htmlButtons = Collections.emptyList();
        while (htmlButtons.isEmpty()) {
            log.debug("div elements is null... waiting");
            webClient.waitForBackgroundJavaScript(60000);
            htmlButtons = htmlPage.getByXPath(
                    "//div[@class='fc-event fc-event-vert fc-event-start fc-event-end']");
        }
        for (HtmlDivision htmlDivision : htmlButtons) {
            log.info(htmlDivision.asText());
        }
    }


    private void clickPreviousIfAvailable() throws IOException {
        HtmlButton htmlButton = getPreviousHtmlButton();
        while (!htmlButton.isDisabled()) {
            htmlPage = htmlButton.click();
            htmlButton = getPreviousHtmlButton();
        }
    }


    private HtmlButton getPreviousHtmlButton() {
        HtmlButton htmlButton = null;
        while (htmlButton == null) {
            log.debug("Previous Button is null... waiting");
            webClient.waitForBackgroundJavaScript(60000);
            htmlButton = (HtmlButton) htmlPage.getElementById("btnPrev");
        }
        return htmlButton;
    }


    @Override
    public boolean hasNext() {
        return false;
    }


    @Override
    public PageNavigationStrategy next() {
        throw new NoSuchElementException("There are no more elements to iterate!");
    }
}
