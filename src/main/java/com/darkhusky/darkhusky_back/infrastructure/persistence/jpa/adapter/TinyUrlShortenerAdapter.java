package com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.adapter;

import com.darkhusky.darkhusky_back.domain.port.out.UrlShortenerPort;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

@Component
public class TinyUrlShortenerAdapter implements UrlShortenerPort {

    @Override
    public String shortenUrl(String longUrl) {
        try {
            String tinyUrlApi = "http://tinyurl.com/api-create.php?url=" + longUrl;
            URL url = new URL(tinyUrlApi);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            try (InputStream in = conn.getInputStream(); Scanner scanner = new Scanner(in)) {
                return scanner.useDelimiter("\\A").next();
            }
        } catch (Exception e) {
            return longUrl;
        }
    }
}
