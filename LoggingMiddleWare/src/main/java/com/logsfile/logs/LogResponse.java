package com.logsfile.logs;

public class LogResponse {
    private String logId;
    private String message;

    public LogResponse(String logId, String message) {
        this.logId = logId;
        this.message = message;
    }

    public String getLogId() { return logId; }
    public String getMessage() { return message; }
}
