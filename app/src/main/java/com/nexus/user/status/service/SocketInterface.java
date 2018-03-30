package com.nexus.user.status.service;

/**
 * Any class want to connect to Socket will implement this interface and write their logic
 */
public interface SocketInterface {

    /**
     * Listen to status updates from socket
     */
    void connectAndListenToStatusUpdates();
}
