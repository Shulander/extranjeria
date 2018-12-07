package us.vicentini.extranjeria.navigation.services;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import us.vicentini.extranjeria.navigation.services.pages.NavigationFactory;
import us.vicentini.extranjeria.navigation.services.pages.PageNavigationStrategy;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Log4j
@Service
@RequiredArgsConstructor
public class ReservaHoraExtranjeriaService {
    private static final String START_PAGE = "https://reservahora.extranjeria.gob.cl/inicio.action";

    private volatile WebClient webClient;

    private final NavigationFactory navigationFactory;

    public void checkAvailableHour() throws IOException {
        try {
            log.info("++++ ReservaHoraExtranjeria ++++");
            HtmlPage htmlPage = webClient.getPage(START_PAGE);

            PageNavigationStrategy page = navigationFactory.getStartPage(htmlPage, webClient);
            while (page.hasNext()) {
                page = page.next();
                page.navigate();
            }
        } finally {
            log.info("---- ReservaHoraExtranjeria ----");
        }
    }


    @PostConstruct
    public void init() {
        webClient = new WebClient(BrowserVersion.FIREFOX_60);
        webClient.getOptions().setTimeout(600000);
        webClient.getOptions().setDownloadImages(false);
        webClient.getOptions().setCssEnabled(false);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
    }
}
