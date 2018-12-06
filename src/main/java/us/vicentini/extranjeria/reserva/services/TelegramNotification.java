package us.vicentini.extranjeria.reserva.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TelegramNotification implements Notification {

    @Value("${telegram.bot.url}")
    private String botUrl;

    @Override
    public void sendMessage(String msg) {
        try {
            log.info(executeGet(botUrl+msg));
        } catch (Exception e) {
            log.error("Error sending Whatsapp message: "+e.getMessage(), e);
        }
    }

    public String executeGet(String urlToRead) throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        return result.toString();
    }
}
