package it.polimi.ingsw;

import it.polimi.ingsw.Network.server.Server;

import java.io.IOException;

public class Main {
    public static void main( String[] args ) throws IOException, InterruptedException {

        Server s = new Server(1234);
        s.startServer();

        /*CliView cliView = new CliView(2);
        Thread thread = new Thread(cliView);
        thread.start();
        */
    }
}