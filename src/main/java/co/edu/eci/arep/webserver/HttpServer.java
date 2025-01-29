package co.edu.eci.arep.webserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
    private static final int PORT = 35000;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Servidor iniciado en el puerto " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nueva conexión recibida");

                RequestHandler.handleRequest(clientSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}