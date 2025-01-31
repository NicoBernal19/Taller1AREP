package co.edu.eci.arep.webserver;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HttpServerTest {

    private static final String SERVER_URL = "http://localhost:35000/app/hello";

    @BeforeAll
    static void waitForServer() throws InterruptedException {
        System.out.println("Esperando a que el servidor se inicie...");
        Thread.sleep(3000);
    }

    // PRUEBAS UNITARIAS PARA ApiService

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
    void testHandlePostRequestWithBody() {
        String body = "name=Pedro";
        BufferedReader reader = new BufferedReader(new StringReader(body));
        String response = ApiService.handleApiRequest("/app/hello", "POST", reader);
        assertTrue(response.contains("\"Hello from POST, Pedro!\""));
    }

    @Test
    void testHandleInvalidMethod() {
        String response = ApiService.handleApiRequest("/app/hello", "DELETE", null);
        assertTrue(response.contains("\"error\": \"Method not allowed\""));
    }

    @Test
    void testHandleUnknownApiEndpoint() {
        String response = ApiService.handleApiRequest("/app/unknown", "GET", null);
        assertTrue(response.contains("\"error\": \"API endpoint not found\""));
    }

    @Test
    void testParsePostDataValid() throws Exception {
        Map<String, String> data = ApiService.parsePostData("name=Lucas&age=30");
        assertEquals("Lucas", data.get("name"));
        assertEquals("30", data.get("age"));
    }

    @Test
    void testParsePostDataInvalidFormat() throws Exception {
        Map<String, String> data = ApiService.parsePostData("invalidData");
        assertTrue(data.isEmpty());
    }

    // PRUEBAS UNITARIAS PARA RequestHandler

    @Test
    void testRequestHandlerWithMockSocket() throws IOException {
        Socket mockSocket = mock(Socket.class);
        BufferedReader mockInput = new BufferedReader(new StringReader("GET /app/hello HTTP/1.1\n\n"));
        when(mockSocket.getInputStream()).thenReturn(new ByteArrayInputStream("GET /app/hello HTTP/1.1\n\n".getBytes()));
        when(mockSocket.getOutputStream()).thenReturn(new ByteArrayOutputStream());

        RequestHandler.handleRequest(mockSocket);

        verify(mockSocket, atLeastOnce()).getInputStream();
        verify(mockSocket, atLeastOnce()).getOutputStream();
    }

    @Test
    void testRequestHandlerPost() throws IOException {
        Socket mockSocket = mock(Socket.class);
        when(mockSocket.getInputStream()).thenReturn(new ByteArrayInputStream(("POST /app/hello HTTP/1.1\nContent-Length: 9\n\nname=Ana").getBytes()));
        when(mockSocket.getOutputStream()).thenReturn(new ByteArrayOutputStream());

        RequestHandler.handleRequest(mockSocket);

        verify(mockSocket, atLeastOnce()).getInputStream();
        verify(mockSocket, atLeastOnce()).getOutputStream();
    }

    // PRUEBAS UNITARIAS PARA FileService

    @Test
    void testFileServiceServeFile() throws IOException {
        PrintWriter out = mock(PrintWriter.class);
        Socket clientSocket = mock(Socket.class);
        when(clientSocket.getOutputStream()).thenReturn(new ByteArrayOutputStream());

        FileService.serveFile("/index.html", out, clientSocket);

        verify(out, atLeastOnce()).println(anyString());
    }

    @Test
    void testFileServiceServeNonExistingFile() throws IOException {
        PrintWriter out = mock(PrintWriter.class);
        Socket clientSocket = mock(Socket.class);
        when(clientSocket.getOutputStream()).thenReturn(new ByteArrayOutputStream());

        FileService.serveFile("/nonexistent.html", out, clientSocket);

        verify(out, atLeastOnce()).println(contains("404 Not Found"));
    }

    // PRUEBAS AUTOMÁTICAS DE INTEGRACIÓN (REQUERIMOS SERVIDOR EN EJECUCIÓN)

    @Test
    void testGetRequest() throws IOException {
        URL url = new URL(SERVER_URL + "?name=Maria");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        assertEquals(200, conn.getResponseCode());
        assertTrue(new String(conn.getInputStream().readAllBytes(), StandardCharsets.UTF_8)
                .contains("\"message\": \"Hello, Maria!\""));
    }

    @Test
    void testPostRequest() throws IOException {
        URL url = new URL(SERVER_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.getOutputStream().write("name=Carlos".getBytes(StandardCharsets.UTF_8));

        assertEquals(200, conn.getResponseCode());
        assertTrue(new String(conn.getInputStream().readAllBytes(), StandardCharsets.UTF_8)
                .contains("\"Hello from POST, Carlos!\""));
    }
}

