package us.vicentini.extranjeria.reserva.services;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import us.vicentini.extranjeria.navigation.services.ReservaHoraExtranjeriaService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservaHoraService {

    private final ReservaHoraExtranjeriaService reservaHoraExtranjeriaService;

    private final CounterTimeWindowService counterTimeWindowService;

    private final Notification telegramNotification;

    @Setter(onMethod_ = @Value("#{'${reserva.invalidStatus}'.split(',')}"))
    private List<String> invalidStatus;

    @PostConstruct
    public void init() {
        telegramNotification.sendMessage("Application has started");
    }

    @PreDestroy
    public void destroy() {
        telegramNotification.sendMessage("Application has been terminated");
    }

    @Scheduled(fixedDelay = 300000, initialDelay = 10000)
    public void scheduledRun() {
        try {
            log.info("Start!!!!");
            reservaHoraExtranjeriaService.checkAvailableHour();
            checkOpenSpot();
            counterTimeWindowService.init();
        } catch (Exception e) {
            log.error("Error executing Reserva Hora: " + e.getMessage(), e);
            sendErrorMessage("Error executing Reserva Hora: " + e.getMessage());
        }
    }


    private void sendErrorMessage(String errorMessage) {
        telegramNotification.sendMessage(errorMessage);
    }


    private void checkOpenSpot() {
        Set<String> statuses = counterTimeWindowService.getStatus();

        Set<String> availableStatus = removeIgnoredStatus(statuses);
        if (!availableStatus.isEmpty()) {
            String message = createMessage(availableStatus);
            telegramNotification.sendMessage(message);
        } else {
            log.info("**** No available Spots ****");
        }
    }


    private String createMessage(Set<String> availableStatus) {
        return "There are available spots with the foolowing status: " + String.join(",", availableStatus);
    }


    private Set<String> removeIgnoredStatus(Set<String> statuses) {
        Set<String> localStatuses = new HashSet<>(statuses);
        localStatuses.removeAll(invalidStatus);
        return localStatuses;
    }
}
