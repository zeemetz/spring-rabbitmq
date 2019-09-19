package com.mycimb.example.rabbitmq.controller;

public class PublishRequest {
    private String[] message;

    public String[] getMessage() {
        return message;
    }

    public void setMessage(String[] message) {
        this.message = message;
    }
}
