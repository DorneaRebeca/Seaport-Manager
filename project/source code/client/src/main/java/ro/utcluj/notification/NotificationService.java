package ro.utcluj.notification;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

@Component
public class NotificationService {

    Socket socket;

    public NotificationService() throws IOException {
        this.socket = new Socket("localhost", 8079);
    }



    public void connectToNotificationServer(String userName) throws IOException {
        Thread listenerThread = new Thread(new ServerSocketListener (socket));
        System.out.println("NOTIFICATION STARTED FOR CLIENT for: " + userName);
        listenerThread.start();
    }

    public void sendMessageToServer(String msg) throws IOException {
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println(msg);
    }



}
