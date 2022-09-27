package ru.promo.javacore.model;

public class Notification<T> {
    private String type;
    private T data;

    public Notification(String type, T data) {
        this.type = type;
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public T getData() {
        return data;
    }
}
