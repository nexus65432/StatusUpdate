package com.nexus.user.status.events;

/**
 * Class which pass only data and who ever receiving this will have to process it.
 */
public class SocketEvents {

    String message;

    public SocketEvents(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
