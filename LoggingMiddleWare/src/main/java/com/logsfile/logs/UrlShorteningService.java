package com.logsfile.logs;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UrlShorteningService {

    private final Map<String, ShortUrlEntry> store = new HashMap<>();

    public ShortUrlResponse createShortUrl(ShortUrlRequest request) {
        String shortcode = (request.getShortcode() == null || request.getShortcode().isBlank())
                ? UUID.randomUUID().toString().substring(0, 6)
                : request.getShortcode();

        int validity = (request.getValidity() == null) ? 30 : request.getValidity();
        Instant expiryTime = Instant.now().plusSeconds(validity * 60L);

        ShortUrlEntry entry = new ShortUrlEntry(request.getUrl(), expiryTime);
        store.put(shortcode, entry);

        String shortLink = "http://localhost:8080/shorturls/" + shortcode;
        String expiry = expiryTime.atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);

        return new ShortUrlResponse(shortLink, expiry);
    }

    public Object getStats(String shortcode) {
        ShortUrlEntry entry = store.get(shortcode);
        if (entry == null) {
            return Map.of("error", "Shortcode not found");
        }
        return Map.of(
                "originalUrl", entry.getUrl(),
                "expiry", entry.getExpiry().toString(),
                "clicks", entry.getClicks()
        );
    }
}
