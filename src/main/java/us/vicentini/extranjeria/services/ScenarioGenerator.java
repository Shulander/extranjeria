package us.vicentini.extranjeria.services;

import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import lombok.extern.log4j.Log4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import us.vicentini.extranjeria.services.navigation.PageNavigationStrategy;
import us.vicentini.extranjeria.services.navigation.StartPage;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Log4j
@Service
public class ScenarioGenerator implements CommandLineRunner {
    private static final String START_PAGE = "https://reservahora.extranjeria.gob.cl/inicio.action";

    private volatile HtmlPage htmlPage;
    private volatile WebClient webClient;


    @Override
    public void run(String... args) throws IOException {
        log.info("Start!!!!");
        htmlPage = webClient.getPage(START_PAGE);

        PageNavigationStrategy page = new StartPage(htmlPage, webClient);
        while (page.hasNext()) {
            page = page.next();
            page.navigate();
        }
    }


    @PostConstruct
    public void init() {
        webClient = new WebClient();
        webClient.getOptions().setCssEnabled(false);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
    }
}
