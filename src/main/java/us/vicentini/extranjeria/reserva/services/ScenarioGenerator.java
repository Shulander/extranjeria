package us.vicentini.extranjeria.reserva.services;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import us.vicentini.extranjeria.navigation.services.ReservaHoraExtranjeriaService;

@Log4j
@Service
@RequiredArgsConstructor
public class ScenarioGenerator implements CommandLineRunner {

    private final ReservaHoraExtranjeriaService reservaHoraExtranjeriaService;

    private final CounterTimeWindowService counterTimeWindowService;

    private final TelegramNotification telegramNotification;

    @Override
    public void run(String... args) throws IOException {
        log.info("Start!!!!");
        reservaHoraExtranjeriaService.checkAvailableHour();
        counterTimeWindowService.print();
        telegramNotification.sendMessage("teste de mensagem vamos ver se funciona ;)");
    }
}
