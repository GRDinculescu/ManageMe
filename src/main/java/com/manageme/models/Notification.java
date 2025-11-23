package com.manageme.models;


public class Notification {
    String message;
    String user;
    String time; // FIXME: Si, string por que me apetece


    public Notification(String time, String user, String message) {
        this.time = time;
        this.user = user;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
