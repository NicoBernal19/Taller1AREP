package co.edu.eci.arep.webserver;

import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;

import org.junit.jupiter.api.*;
//import org.mockito.Mockito;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpServerTest {

    @Test
    void testHandleGetRequestWithName() {
        String response = ApiService.handleApiRequest("/app/hello?name=Juan", "GET", null);
        assertTrue(response.contains("\"message\": \"Hello, Juan!\""));
    }

    @Test
    void testHandleGetRequestWithoutName() {
        String response = ApiService.handleApiRequest("/app/hello", "GET", null);
        assertTrue(response.contains("\"message\": \"Hello, Unknown!\""));
    }

    @Test
    void testHandleInvalidMethod() {
        String response = ApiService.handleApiRequest("/app/hello", "DELETE", null);
        assertTrue(response.contains("\"error\": \"Method not allowed\""));
    }
}

