package com.logsfile.logs;

import java.time.Instant;

public class ShortUrlEntry {
    private String url;
    private Instant expiry;
    private int clicks;

    public ShortUrlEntry(String url, Instant expiry) {
        this.url = url;
        this.expiry = expiry;
        this.clicks = 0;
    }

    public String getUrl() { return url; }
    public Instant getExpiry() { return expiry; }
    public int getClicks() { return clicks; }
    public void incrementClicks() { this.clicks++; }
}
