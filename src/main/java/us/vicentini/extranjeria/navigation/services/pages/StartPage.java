package us.vicentini.extranjeria.navigation.services.pages;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StartPage extends BasePageNavigation {

    public StartPage(HtmlPage htmlPage, WebClient webClient, NavigationFactory navigationFactory) {
        super(htmlPage, webClient, navigationFactory);
    }


    @Override
    protected void navigateImpl() {
        throw new UnsupportedOperationException("This is the start page and doesn't have an implementation");
    }


    @Override
    public PageNavigationStrategy next() {
        HtmlSpan myName = (HtmlSpan) htmlPage.getElementById("spanDetalle");
        if(myName == null) {
            return navigationFactory.getLoginPage(htmlPage, webClient);
        } else {
            return navigationFactory.getBienvenidoPage(htmlPage, webClient);
        }
    }

}
