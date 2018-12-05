package us.vicentini.extranjeria.navigation.services.pages;

import java.io.IOException;
import java.util.Iterator;

public interface PageNavigationStrategy extends Iterator<PageNavigationStrategy> {

    void navigate() throws IOException;
}
