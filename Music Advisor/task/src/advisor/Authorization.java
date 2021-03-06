package advisor;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Class used for authorization:
 * getting access code and access token
 * Only for specified CLIENT_ID and CLIENT_SECRET
 * You should change those values if you want to make that application work for you.
 */
public class Authorization {
    public static String SERVER_PATH = "https://accounts.spotify.com";
    public static String REDIRECT_URI = "http://localhost:8080";
    public static String CLIENT_ID = "81872b06315f4b9eaff3ead664f3c376";
    public static String CLIENT_SECRET = "e9b7373e4b3d418f94bddcba8332861d";
    public static String ACCESS_CODE = "";
    public static String ACCESS_TOKEN = "";
    static String uri = SERVER_PATH + "/authorize" + "?client_id=" + CLIENT_ID + "&redirect_uri=" + REDIRECT_URI + "&response_type=code";

    static void GetAccessCode() throws IOException, InterruptedException{

        HttpServer server = HttpServer.create();
        server.bind(new InetSocketAddress(8080), 0);

        server.createContext("/",
                new HttpHandler() {
                    public void handle(HttpExchange exchange) throws IOException {
                        String query = exchange.getRequestURI().getQuery();
                        String request;
                        if (query != null && query.contains("code")) {
                            ACCESS_CODE = query.substring(5);
                            System.out.println("code received\n" +
                                    ACCESS_CODE);
                            request = "Got the code. Return back to your program.";
                        } else {
                            request = "Authorization code not found. Try again.";
                        }
                        exchange.sendResponseHeaders(200, request.length());
                        exchange.getResponseBody().write(request.getBytes());
                        exchange.getResponseBody().close();
                    }
                }
        );
        server.start();

        System.out.println("use this link to request the access code:\n" + uri);

        System.out.println("waiting for code...");
        while (ACCESS_CODE.length() == 0) {
            Thread.sleep(100);
        }
        server.stop(5);

    }

    static void GetAccessToken() throws IOException, InterruptedException{

        System.out.println("making http request for access token...\n" + "response:");

        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .uri(URI.create(SERVER_PATH + "/api/token"))
                .POST(HttpRequest.BodyPublishers.ofString("grant_type=authorization_code"
                        + "&code=" + ACCESS_CODE
                        + "&client_id=" + CLIENT_ID
                        + "&client_secret=" + CLIENT_SECRET
                        + "&redirect_uri=" + REDIRECT_URI))
                .build();

        HttpClient client = HttpClient.newBuilder().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JsonObject jo = JsonParser.parseString(response.body()).getAsJsonObject();

        ACCESS_TOKEN = jo.get("access_token").getAsString();

        System.out.println(response.body());
        System.out.println("---SUCCESS---");

    }
}
