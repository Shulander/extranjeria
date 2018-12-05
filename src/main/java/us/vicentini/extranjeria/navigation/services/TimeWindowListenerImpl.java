package us.vicentini.extranjeria.navigation.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Service;
import us.vicentini.extranjeria.navigation.domain.TimeWindow;

@Slf4j
@Service
@ConditionalOnMissingBean(type = "TimeWindowListener")
class TimeWindowListenerImpl implements TimeWindowListener {
    @Override
    public void publish(TimeWindow newTime) {
        log.info("+ "+newTime);
    }
}
