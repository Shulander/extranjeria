package us.vicentini.extranjeria.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Log4j
@Service
@RequiredArgsConstructor
public class ScenarioGenerator implements CommandLineRunner {

    private final ReservaHoraExtranjeriaService reservaHoraExtranjeriaService;


    @Override
    public void run(String... args) throws IOException {
        log.info("Start!!!!");
        reservaHoraExtranjeriaService.checkAvailableHour();
    }
}
