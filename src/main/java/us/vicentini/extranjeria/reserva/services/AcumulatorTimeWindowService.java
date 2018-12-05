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

@Slf4j
@Service
public class AcumulatorTimeWindowService implements TimeWindowListener {
    private Map<String, List<TimeWindow>> statusAcumulator;

    @PostConstruct
    public void init() {
        statusAcumulator = new LinkedHashMap<>();
    }

    @Override
    public void publish(TimeWindow newTime) {

        if(!statusAcumulator.containsKey(newTime.getStatus())) {
            statusAcumulator.put(newTime.getStatus(), new LinkedList<>());
        }

        statusAcumulator.get(newTime.getStatus()).add(newTime);
    }


    public void print() {
        for (Map.Entry<String, List<TimeWindow>> stringListEntry : statusAcumulator.entrySet()) {
            log.info("*************************");
            log.info(stringListEntry.getKey());
            log.info("*************************");
            for (TimeWindow timeWindow : stringListEntry.getValue()) {
                log.info(timeWindow.toString());
            }
            log.info("*************************");
        }
    }
}
