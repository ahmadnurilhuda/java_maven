package com.greenacademy;

import org.springframework.stereotype.Component;

@Component
public class LoggerService {

    public void loggerError(String message){
        System.out.println("\n\u001B[31m[EROOR]"+message+"\u001B[0m");
    }

    public void loggerSuccess(String message){
        System.out.println("\n\u001B[32m[SUCCESS]"+message+"\u001B[0m\n");
    }
    public void loggerInfo(String message){
        System.out.println("\n\u001B[34m[INFO]"+message+"\u001B[0m\n");
    }
    public void loggerWarning(String message){
        System.out.println("\n\u001B[33m[WARNING]"+message+"\u001B[0m\n");
    }
}
