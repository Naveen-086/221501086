package com.logsfile.logs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import java.util.UUID;

@Service
public class LoggingService {

    private final RestTemplate restTemplate;
    private final String LOGGING_API_URL = "http://20.244.56.144/evaluation-service/logs";

    @Autowired
    public LoggingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public LogResponse log(String stack, String level, String packageName, String message, String token) {
        LogRequest logRequest = new LogRequest(stack, level, packageName, message);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<LogRequest> requestEntity = new HttpEntity<>(logRequest, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    LOGGING_API_URL,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );

            // ✅ Check for success response
            if (response.getStatusCode().is2xxSuccessful()) {
                String logId = UUID.randomUUID().toString();
                return new LogResponse(logId, "Log created successfully");
            } else {
                return new LogResponse(null, "Failed with status: " + response.getStatusCode());
            }

        } catch (HttpClientErrorException.Unauthorized e) {
            // 401 error
            return new LogResponse(null, "Unauthorized: Invalid bearer token");
        } catch (HttpClientErrorException.BadRequest e) {
            // 400 error
            return new LogResponse(null, "Bad Request: " + e.getResponseBodyAsString());
        } catch (HttpStatusCodeException e) {
            // other HTTP errors
            return new LogResponse(null, "HTTP Error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
        } catch (Exception e) {
            // unexpected errors
            return new LogResponse(null, "Error: " + e.getMessage());
        }
    }
}
