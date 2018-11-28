package us.vicentini.extranjeria.services.navigation;

import java.io.IOException;
import java.util.Iterator;

public interface PageNavigationStrategy extends Iterator<PageNavigationStrategy> {

    void navigate() throws IOException;
}
