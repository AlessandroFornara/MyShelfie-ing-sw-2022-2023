package it.polimi.ingsw.Network.server;

import java.io.IOException;

/**
 * This class allows to manage parallel client enrollments.
 * @author Alessandro Fornara
 */

public class ClientManager implements Runnable {

    private final ServerManager serverManager;
    private final int number;

    ClientManager(ServerManager serverManager, int number) {
        this.serverManager = serverManager;
        this.number = number;
    }

    @Override
    public void run() {
        try {
            serverManager.addClientToLog(this.number);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}