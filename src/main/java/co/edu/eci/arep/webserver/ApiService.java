package co.edu.eci.arep.webserver;

public class ApiService {
    public static String handleApiRequest(String path) {
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
        } else {
            return "HTTP/1.1 404 Not Found\r\n" +
                    "Content-Type: application/json\r\n" +
                    "\r\n" +
                    "{\"error\": \"API endpoint not found\"}";
        }
    }
}
