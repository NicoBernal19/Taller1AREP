package co.edu.eci.arep.webserver;

import java.io.*;
import java.net.Socket;

import java.io.*;
import java.net.Socket;

public class RequestHandler {
    public static void handleRequest(Socket clientSocket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String inputLine, requestLine = "";
            boolean isFirstLine = true;
            int contentLength = 0;

            // Leer cabecera HTTP
            while ((inputLine = in.readLine()) != null && !inputLine.isEmpty()) {
                if (isFirstLine) {
                    requestLine = inputLine;
                    isFirstLine = false;
                }
                if (inputLine.startsWith("Content-Length:")) {
                    contentLength = Integer.parseInt(inputLine.split(":")[1].trim());
                }
            }

            // Procesar primera línea: "METHOD /ruta HTTP/1.1"
            String[] requestParts = requestLine.split(" ");
            if (requestParts.length < 2) return;
            String method = requestParts[0];  // GET o POST
            String path = requestParts[1];   // /app/hello o /index.html

            // Leer cuerpo de la petición si es POST
            BufferedReader requestBody = null;
            if (method.equals("POST") && contentLength > 0) {
                char[] bodyChars = new char[contentLength];
                in.read(bodyChars, 0, contentLength);
                requestBody = new BufferedReader(new StringReader(new String(bodyChars)));
            }

            // Enviar la petición a ApiService o servir un archivo
            if (path.startsWith("/app")) {
                out.println(ApiService.handleApiRequest(path, method, requestBody));
            } else {
                FileService.serveFile(path, out, clientSocket);
            }

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
