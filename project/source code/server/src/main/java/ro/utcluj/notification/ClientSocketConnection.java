package ro.utcluj.notification;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

class ClientSocketConnection {

    private final NotificationService notificationService;
    private BufferedReader in;
    private PrintWriter out;


    ClientSocketConnection(Socket clientSocket, NotificationService notificationService) {

        this.notificationService = notificationService;
        createReaderAndWriter (clientSocket);
        new Thread (this::listenAndRespond).start (); // starting a new thread which listens to client messages
    }

    private void createReaderAndWriter(Socket clientSocket) {
        try {
            in = new BufferedReader (new InputStreamReader (clientSocket.getInputStream ()));
            out = new PrintWriter (clientSocket.getOutputStream (), true);
        } catch (Exception exception) {
            System.out.println ("Client disconnected");
        }
    }

    private void listenAndRespond() {
        try {
            while (true) {
                String received = in.readLine();

                if(received.contains ("login")){
                    String []list = received.split (" ");
                    if (!notificationService.connectionConcurrentHashMap.contains(this)) {
                        notificationService.connectionConcurrentHashMap.put(list[1], this);
                        notificationService.connectedClients.add (this);
                        System.out.println ("Logged client no "+ list[1]);
                    }
                }
                else if(received.contains ("logout")){
                    String []list = received.split(" ");
                    notificationService.connectionConcurrentHashMap.remove(list[1]);
                    notificationService.removeClient(this);
                }else{
                    String []list = received.split(" ");
                    String key = new String();
                    for(String i : list){
                        if(i.startsWith ("idclient=")){
                            key = i.substring (9);
                        }
                    }
                    System.out.println ("&&&&&-->" + key);
                    notificationService.sendMessageToAClient (key, "Appointment to cargo service has been modified!");
                }
            }
        } catch (Exception exception) {
            System.out.println("Client disconnected");
            notificationService.removeClient(this);
        }
    }


    void sendMessage(String message) {
        out.println(message);
    }

}