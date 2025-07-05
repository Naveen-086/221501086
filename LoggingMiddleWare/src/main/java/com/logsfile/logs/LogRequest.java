package com.logsfile.logs;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LogRequest {
    private String stack;
    private String level;

    @JsonProperty("package")  // Map packageName to "package" in JSON
    private String packageName;

    private String message;

    // Constructors
    public LogRequest() {}
    public LogRequest(String stack, String level, String packageName, String message) {
        this.stack = stack;
        this.level = level;
        this.packageName = packageName;
        this.message = message;
    }

    // Getters and setters
    public String getStack() { return stack; }
    public void setStack(String stack) { this.stack = stack; }

    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }

    public String getPackageName() { return packageName; }
    public void setPackageName(String packageName) { this.packageName = packageName; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
