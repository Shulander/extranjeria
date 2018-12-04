package us.vicentini.extranjeria.services.navigation;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class StartPage extends BasePageNavigation {

    public StartPage(HtmlPage htmlPage, WebClient webClient) {
        super(htmlPage, webClient);
    }


    @Override
    protected void navigateImpl() {
        throw new UnsupportedOperationException("This is the start page and doesn't have an implementation");
    }


    @Override
    public PageNavigationStrategy next() {
        return new LoginPage(htmlPage, webClient);
    }

}
