package com.logsfile.logs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/example")
public class ExampleController {

    @Autowired
    private LoggingService loggingService;

    @PostMapping("/log")
    public LogResponse logMessage(@RequestHeader("Authorization") String authHeader,
                                  @RequestBody LogRequest request) {

        String token = authHeader.replace("Bearer ", "").trim();

        // ✅ Return the actual result of the logging attempt
        return loggingService.log(
                request.getStack(),
                request.getLevel(),
                request.getPackageName(),
                request.getMessage(),
                token
        );
    }
}
