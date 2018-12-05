package us.vicentini.extranjeria.services.navigation;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;


@Slf4j
@RequiredArgsConstructor
abstract class BasePageNavigation implements PageNavigationStrategy {
    @NonNull
    protected HtmlPage htmlPage;
    protected final WebClient webClient;
    protected final NavigationFactory navigationFactory;

    @Override
    public final void navigate() throws IOException {
        navigateImpl();
    }


    @Override
    public boolean hasNext() {
        return true;
    }


    protected abstract void navigateImpl() throws IOException;
}
