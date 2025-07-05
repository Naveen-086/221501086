package com.logsfile.logs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shorturls")
public class UrlShorteningController {

    @Autowired
    private UrlShorteningService urlShorteningService;

    @PostMapping
    public ShortUrlResponse createShortUrl(@RequestBody ShortUrlRequest request) {
        return urlShorteningService.createShortUrl(request);
    }

    @GetMapping("/{shortcode}")
    public Object getShortUrlStats(@PathVariable String shortcode) {
        return urlShorteningService.getStats(shortcode);
    }
}
