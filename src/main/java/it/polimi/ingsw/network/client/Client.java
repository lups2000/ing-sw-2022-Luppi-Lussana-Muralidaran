package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.Messages.ServerSide.Error;
import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.ServerSide.Ping;
import it.polimi.ingsw.observer.Observable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;


/**
 * This class represents a socket client implementation.
 */
public class Client extends Observable {

    public static final Logger LOGGER = Logger.getLogger(Client.class.getName());

    private final Socket socket;

    private final ObjectOutputStream outputStm;
    private final ObjectInputStream inputStm;
    private final ExecutorService readExecutionQueue;
    private final ScheduledExecutorService pinger;

    private static final int SOCKET_TIMEOUT = 10000;

    public Client(String ipAddress, int port) throws IOException {
        this.socket = new Socket();
        this.socket.connect(new InetSocketAddress(ipAddress, port), SOCKET_TIMEOUT);
        this.outputStm = new ObjectOutputStream(socket.getOutputStream());
        this.inputStm = new ObjectInputStream(socket.getInputStream());
        this.readExecutionQueue = Executors.newSingleThreadExecutor();
        this.pinger = Executors.newSingleThreadScheduledExecutor();
    }

    /**
     * Asynchronously reads a message from the server via socket and notifies the ClientController.
     */
    public void readMessage() {
        readExecutionQueue.execute(() -> {

            while (!readExecutionQueue.isShutdown()) {
                Message message = null;
                try {
                    message = (Message) inputStm.readObject();
                    Client.LOGGER.info("Received: " + message);
                } catch (IOException | ClassNotFoundException e) {
                    message = new Error( "Connection lost with the server.");
                    disconnect();
                    readExecutionQueue.shutdownNow();
                    e.printStackTrace();
                }
                notifyObserver(message);
            }
        });
    }

    /**
     * Sends a message to the server via socket.
     *
     * @param message the message to be sent.
     */
    public void sendMessage(Message message) {
        try {
            outputStm.writeObject(message);
            outputStm.reset();
        } catch (IOException e) {
            disconnect();
            notifyObserver(new Error( "Could not send message."));
        }
    }

    /**
     * Disconnect the socket from the server.
     */
    public void disconnect() {
        try {
            if (!socket.isClosed()) {
                readExecutionQueue.shutdownNow();
                //activatePing(false);
                socket.close();
            }
        } catch (IOException e) {
            notifyObserver(new Error("Could not disconnect."));
        }
    }

    /**
     * Enable a heartbeat (ping messages) between client and server sockets to keep the connection alive.
     *
     * @param isActive set this argument to {@code true} to enable the heartbeat.
     *                set to {@code false} to kill the heartbeat.

    public void activatePing(boolean isActive) {
        if (isActive) {
            pinger.scheduleAtFixedRate(() -> sendMessage(new Ping()), 0, 1000, TimeUnit.MILLISECONDS);
        } else {
            pinger.shutdownNow();
        }
    }*/
}