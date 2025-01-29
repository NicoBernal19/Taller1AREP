package co.edu.eci.arep.webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;

public class FileService {
    public static void serveFile(String filePath, PrintWriter out, Socket clientSocket) {
        if (filePath.equals("/")) filePath = "/index.html";
        String basePath = "webapp";
        File file = new File(basePath + filePath);

        if (file.exists() && !file.isDirectory()) {
            try {
                String mimeType = getMimeType(filePath);
                byte[] fileContent = Files.readAllBytes(file.toPath());

                out.println("HTTP/1.1 200 OK");
                out.println("Content-Type: " + mimeType);
                out.println("Content-Length: " + fileContent.length);
                out.println();
                out.flush();
                clientSocket.getOutputStream().write(fileContent);
                clientSocket.getOutputStream().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            out.println("HTTP/1.1 404 Not Found");
            out.println("Content-Type: text/html");
            out.println();
            out.println("<html><body><h1>404 Not Found</h1></body></html>");
        }
    }

    private static String getMimeType(String filePath) {
        if (filePath.endsWith(".html")) return "text/html";
        if (filePath.endsWith(".css")) return "text/css";
        if (filePath.endsWith(".js")) return "application/javascript";
        if (filePath.endsWith(".png")) return "image/png";
        if (filePath.endsWith(".jpg") || filePath.endsWith(".jpeg")) return "image/jpeg";
        return "application/octet-stream";
    }
}

