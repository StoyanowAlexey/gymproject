package org.example.exceptions;

public class FakeObjectException extends Exception{
    public String getMessage(){
        return "Exception: object doesnt exist or it can not be deleted!";
    }
}
