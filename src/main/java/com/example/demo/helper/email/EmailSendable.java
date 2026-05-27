package com.example.demo.helper.email;

public interface EmailSendable {
    void send(String message) throws InterruptedException;
}
