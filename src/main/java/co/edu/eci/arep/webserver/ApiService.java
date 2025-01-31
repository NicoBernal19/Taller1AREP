package co.edu.eci.arep.webserver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class ApiService {
    public static String handleApiRequest(String path, String method, BufferedReader requestBody) {
        if (method.equals("GET")) {
            return handleGetRequest(path);
        } else if (method.equals("POST")) {
            return handlePostRequest(requestBody);
        } else {
            return "HTTP/1.1 405 Method Not Allowed\r\n" +
                    "Content-Type: application/json\r\n" +
                    "\r\n" +
                    "{\"error\": \"Method not allowed\"}";
        }
    }

    private static String handleGetRequest(String path) {
        if (path.startsWith("/app/hello")) {
            String name = "Unknown";
            int queryIndex = path.indexOf("?");

            if (queryIndex != -1 && queryIndex < path.length() - 1) {
                String[] params = path.substring(queryIndex + 1).split("&");
                for (String param : params) {
                    String[] keyValue = param.split("=");
                    if (keyValue[0].equals("name") && keyValue.length > 1) {
                        name = keyValue[1];
                    }
                }
            }
            return "HTTP/1.1 200 OK\r\n" +
                    "Content-Type: application/json\r\n" +
                    "\r\n" +
                    "{\"message\": \"Hello, " + name + "!\"}";
        }
        return notFoundResponse();
    }

    private static String handlePostRequest(BufferedReader requestBody) {
        try {
            StringBuilder body = new StringBuilder();
            String line;
            while ((line = requestBody.readLine()) != null && !line.isEmpty()) {
                body.append(line);
            }

            Map<String, String> data = parsePostData(body.toString());
            String name = data.getOrDefault("name", "Unknown");

            return "HTTP/1.1 200 OK\r\n" +
                    "Content-Type: application/json\r\n" +
                    "\r\n" +
                    "{\"message\": \"Hello from POST, " + name + "!\"}";

        } catch (Exception e) {
            return "HTTP/1.1 500 Internal Server Error\r\n" +
                    "Content-Type: application/json\r\n" +
                    "\r\n" +
                    "{\"error\": \"Failed to read POST data\"}";
        }
    }

    static Map<String, String> parsePostData(String body) throws Exception {
        Map<String, String> data = new HashMap<>();
        String[] pairs = body.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length > 1) {
                data.put(URLDecoder.decode(keyValue[0], "UTF-8"), URLDecoder.decode(keyValue[1], "UTF-8"));
            }
        }
        return data;
    }

    private static String notFoundResponse() {
        return "HTTP/1.1 404 Not Found\r\n" +
                "Content-Type: application/json\r\n" +
                "\r\n" +
                "{\"error\": \"API endpoint not found\"}";
    }
}
