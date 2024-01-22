package dev.ozkan.labreport.services.logger;

import org.springframework.stereotype.Component;

@Component
public class SimpleLogger implements Logger{

    @Override
    public void debug(String message) {
        String prefix = "Debug :";
        System.out.println(prefix + message);
    }

    @Override
    public void error(String message) {
        String prefix = "Error :";
        System.out.println(prefix + message);
    }

    @Override
    public void info(String message) {
        String prefix = "Info :";
        System.out.println(prefix + message);
    }
}
