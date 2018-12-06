package us.vicentini.extranjeria.reserva.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import us.vicentini.extranjeria.navigation.domain.TimeWindow;
import us.vicentini.extranjeria.navigation.services.TimeWindowListener;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
class CounterTimeWindowService implements TimeWindowListener {
    private Map<String, List<TimeWindow>> statusCounter;

    @PostConstruct
    public void init() {
        statusCounter = new LinkedHashMap<>();
    }

    @Override
    public void publish(TimeWindow newTime) {

        if(!statusCounter.containsKey(newTime.getStatus())) {
            statusCounter.put(newTime.getStatus(), new LinkedList<>());
        }

        statusCounter.get(newTime.getStatus()).add(newTime);
    }


    public void print() {
        for (Map.Entry<String, List<TimeWindow>> stringListEntry : statusCounter.entrySet()) {
            log.info("*************************");
            log.info(stringListEntry.getKey());
            log.info("*************************");
            for (TimeWindow timeWindow : stringListEntry.getValue()) {
                log.info(timeWindow.toString());
            }
            log.info("*************************");
        }
    }


    public Set<String> getStatus() {
        return statusCounter.keySet();
    }
}
