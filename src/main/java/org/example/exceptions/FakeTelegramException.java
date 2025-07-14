package org.example.exceptions;

public class FakeTelegramException extends Exception{
    public String getMessage(){
        return "Exception : name starts without ' @ '  !";
    }
}
