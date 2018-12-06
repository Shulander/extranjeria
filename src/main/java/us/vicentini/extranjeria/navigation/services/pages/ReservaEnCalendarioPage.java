package us.vicentini.extranjeria.navigation.services.pages;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import us.vicentini.extranjeria.navigation.domain.TimeWindow;
import us.vicentini.extranjeria.navigation.services.TimeWindowListener;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
class ReservaEnCalendarioPage extends BasePageNavigation {

    @Setter
    private TimeWindowListener[] timeWindowListener;

    ReservaEnCalendarioPage(HtmlPage htmlPage, WebClient webClient, NavigationFactory navigationFactory) {
        super(htmlPage, webClient, navigationFactory);
    }


    @Override
    protected void navigateImpl() throws IOException {
        log.debug("Start Reserva En Calendario");
        clickPreviousIfAvailable();
        do {
            checkAvailableTimes();
        } while (nextPage());
        log.debug("Finish Reserva En Calendario");
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
        String title = getDataTitle();
        printDateTitle(title);
        for (HtmlDivision htmlDivision : htmlButtons) {
            TimeWindow timeWindow = parseTime(title, htmlDivision.asText());
            log.debug(timeWindow.toString());
            notifyListeners(timeWindow);
        }
    }


    private void notifyListeners(TimeWindow timeWindow) {
        if(timeWindowListener != null) {
            for (TimeWindowListener windowListener : timeWindowListener) {
                windowListener.publish(timeWindow);
            }
        }
    }


    private TimeWindow parseTime(String title, String hour) {
        String[] array = hour.split(" - ");
        return new TimeWindow(title, array[0].trim(), array[1].trim());
    }

    private String getDataTitle() {
        List<HtmlSpan> titleSpan= htmlPage.getByXPath("//span[@class='fc-header-title']");
        if(!titleSpan.isEmpty()) {
            return titleSpan.iterator().next().asText();
        }
        return null;
    }


    private void printDateTitle(String title) {
        if(!StringUtils.isEmpty(title)) {
            log.debug("*******************************");
            log.debug(title);
            log.debug("*******************************");
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
