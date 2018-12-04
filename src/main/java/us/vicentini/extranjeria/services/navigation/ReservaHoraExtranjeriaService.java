package us.vicentini.extranjeria.services.navigation;

import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Log4j
@Service
public class ReservaHoraExtranjeriaService {
    private static final String START_PAGE = "https://reservahora.extranjeria.gob.cl/inicio.action";

    private volatile WebClient webClient;

    public void checkAvailableHour() throws IOException {
        log.info("++++ ReservaHoraExtranjeria ++++");
        HtmlPage htmlPage = webClient.getPage(START_PAGE);

        PageNavigationStrategy page = new StartPage(htmlPage, webClient);
        while (page.hasNext()) {
            page = page.next();
            page.navigate();
        }
        log.info("---- ReservaHoraExtranjeria ----");
    }


    @PostConstruct
    public void init() {
        webClient = new WebClient();
        webClient.getOptions().setCssEnabled(false);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
    }
}
