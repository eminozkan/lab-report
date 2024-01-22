package dev.ozkan.labreport.services.logger;

public interface Logger {
    void debug(String message);

    void error(String message);

    void info(String message);
}
