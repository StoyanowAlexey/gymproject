package org.example.exceptions.exception;

import jakarta.mail.Message;

public class TelegramSendEmailException extends Exception{
    public String getMessage(){
        return "❌Bad request: wrong email!";
    }
}
