package com.web.mockserver.controllers;

import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping()
public class MockController {

    private final Pattern URL_ENCODED_PATTERN = Pattern.compile("%[0-9A-F]{2}");

    private final RateLimiterRegistry rateLimiterRegistry;

    public MockController(RateLimiterRegistry rateLimiterRegistry) {
        this.rateLimiterRegistry = rateLimiterRegistry;
    }

    @GetMapping
    @RateLimiter(name="RateLimiter", fallbackMethod = "handleTooManyRequests")
    public ResponseEntity<String> getMockHtml(@RequestParam String link) throws IOException {
        if(link.charAt(link.length()-1) == '/'){
            link = link.substring(0, link.length()-1);
        }
        String[] parts = link.split("/");
        ClassPathResource resource = new ClassPathResource("static/"+ encodeToLowerCase(parts[parts.length-1]));
        byte[] htmlBytes = Files.readAllBytes(Paths.get(resource.getURI()));
        return ResponseEntity.ok(new String(htmlBytes));
    }

    public ResponseEntity<String> handleTooManyRequests(String link, Throwable throwable) {
        long retryAfter = rateLimiterRegistry.rateLimiter("RateLimiter")
                .getRateLimiterConfig().getLimitRefreshPeriod().toSeconds();
        var headers = new HttpHeaders();
        headers.add("Retry-After", String.valueOf(retryAfter));
        return ResponseEntity.status(429).headers(headers).body("Too many requests");
    }

    //Decode the url and convert all the coded letter to lowercase
    private String encodeToLowerCase(String url) {
        String encodedUrl = URLEncoder.encode(url, StandardCharsets.UTF_8);

        Matcher matcher = URL_ENCODED_PATTERN.matcher(encodedUrl);
        StringBuilder sb = new StringBuilder();

        while (matcher.find()) {
            String lowerCaseMatch = matcher.group().toLowerCase();
            matcher.appendReplacement(sb, lowerCaseMatch);
        }
        matcher.appendTail(sb);

        return sb.toString();
    }
}