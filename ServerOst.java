package sample;

import java.net.*;
import java.io.*;

public class ServerOst {
    private ServerSocket socket = null;
    private Socket clientSocket = null;
    private DataInputStream consoleInput = null;
    public static String getMessage(Socket socket) throws IOException {
        DataInputStream inFromClient = new DataInputStream(socket.getInputStream());
        String message = inFromClient.readUTF();
        return message;
    }
    public static void sendRes(Socket socket, String res) throws IOException {
        DataOutputStream toClient = new DataOutputStream(socket.getOutputStream());
        toClient.writeUTF(res);
    }
    public Socket start(int port) throws IOException {
        try {
            socket = new ServerSocket(port);
            clientSocket = socket.accept();
            consoleInput = new DataInputStream(new BufferedInputStream(System.in));
            if (clientSocket != null) {
                System.out.println("connected");
            }
        } catch (UnknownHostException i) {
            System.out.println(i);
        }
        return clientSocket;
    }

    public void stop(boolean des) throws IOException {
        if (des == false) {
            socket.close();
        }
    }
    public static void main(String[] args) throws IOException {
        ServerOst server = new ServerOst();
        Socket socket = server.start(5000);
        boolean des = true;
        while (des != false) {
            System.out.println(getMessage(socket));
            server.sendRes(socket,"Message sent");
            System.out.println("please input false to stop the server");
            des = Boolean.parseBoolean(server.consoleInput.readLine());
            server.stop(des);
        }
    }

}

