package us.vicentini.extranjeria.navigation.services;

import us.vicentini.extranjeria.navigation.domain.TimeWindow;

@FunctionalInterface
public interface TimeWindowListener {

    void publish(TimeWindow newTime);
}
