package ro.utcluj.notification;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerSocketListener implements Runnable {

    private Socket serverSocket;

    public ServerSocketListener(Socket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader (serverSocket.getInputStream()));
            while (true) {
                String message = in.readLine();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("NOTIFICATION");
                        alert.setContentText(message);
                        alert.showAndWait();
                    }
                });
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("The server is no longer available");
        }
    }
}