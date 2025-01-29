package co.edu.eci.arep.webserver;

import java.io.*;
import java.net.Socket;

public class RequestHandler {
    public static void handleRequest(Socket clientSocket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String inputLine, requestLine = "";
            boolean isFirstLine = true;

            while ((inputLine = in.readLine()) != null) {
                if (isFirstLine) {
                    requestLine = inputLine;
                    isFirstLine = false;
                }
                if (!in.ready()) break;
            }

            String[] requestParts = requestLine.split(" ");
            if (requestParts.length < 2) return;
            String method = requestParts[0];
            String path = requestParts[1];

            if (path.startsWith("/app")) {
                out.println(ApiService.handleApiRequest(path));
            } else {
                FileService.serveFile(path, out, clientSocket);
            }

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
