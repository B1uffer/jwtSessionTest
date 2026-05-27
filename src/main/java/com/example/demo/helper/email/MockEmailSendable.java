package com.example.demo.helper.email;

public class MockEmailSendable implements EmailSendable{
    @Override
    public void send(String message) throws InterruptedException {
        System.out.println("Mock email send complete!");
    }
}
