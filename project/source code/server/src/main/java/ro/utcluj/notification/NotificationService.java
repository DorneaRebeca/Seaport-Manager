package ro.utcluj.notification;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@EnableScheduling
public class NotificationService {
    private static final int PORT_NUMBER = 8079;

    public List<ClientSocketConnection> connectedClients = new CopyOnWriteArrayList<> ();
    public ConcurrentHashMap<String,ClientSocketConnection> connectionConcurrentHashMap = new ConcurrentHashMap<>();

    public NotificationService() {
        System.out.println();
    }

    @PostConstruct
    public void startServer() {
        Thread thread = new Thread(this::startNotificationServer); // starts a thread which runs the startNotificationServer method
        thread.start();
    }

    private void startNotificationServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT_NUMBER)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientSocketConnection clientConnection = new ClientSocketConnection(clientSocket, this);
                connectedClients.add(clientConnection);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessageToAllClients(String message) {

        // ID ADMIN
        ClientSocketConnection clientSocketConnectionAdmin = connectionConcurrentHashMap.get("1");


        for (ClientSocketConnection connection : connectedClients) {
            if (!connection.equals(clientSocketConnectionAdmin))
                connection.sendMessage(message);
        }
    }


    public void sendMessageToAClient(String id, String message){
        ClientSocketConnection clientSocketConnectionAdmin = connectionConcurrentHashMap.get("1");

        if (!connectionConcurrentHashMap.get (id).equals(clientSocketConnectionAdmin) && connectedClients.contains (connectionConcurrentHashMap.get (id)))
            connectionConcurrentHashMap.get (id).sendMessage(message);
    }




    void removeClient(ClientSocketConnection clientConnection) {

        connectedClients.remove(clientConnection);
    }

}
