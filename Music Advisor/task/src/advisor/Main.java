package advisor;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import  java.net.URI;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {

    static boolean access;
    static View view = new View();
    static Categories categories;
    static Features features;
    static NewReleases newReleases;
    static Playlists playlists;

    //public static String SERVER_PATH = "https://accounts.spotify.com";
    //public static String API_SERVER_PATH = "https://api.spotify.com";
    // static String REDIRECT_URI = "http://localhost:8080";
    //public static String CLIENT_ID = "81872b06315f4b9eaff3ead664f3c376";
    //public static String CLIENT_SECRET = "e9b7373e4b3d418f94bddcba8332861d";
    //public static String ACCESS_CODE = "";
    //public static String ACCESS_TOKEN = "";
    //static String uri = SERVER_PATH + "/authorize" + "?client_id=" + CLIENT_ID + "&redirect_uri=" + REDIRECT_URI + "&response_type=code";
    //public static String categories_uri = "/v1/browse/categories";
    //public static String playlist_uri = "v1/browse/categories";
    //public static String new_releases_uri = "/v1/browse/new-releases";
    //public static String featured_uri = "/v1/browse/featured-playlists";

    public static void main(String[] args) {

        //TODO sprawdzenie poprawnośći wejścia
        for(int i = 0; i < args.length; i+= 2) {
            switch(args[i]){
                case "-access":
                    Authorization.SERVER_PATH = args[i + 1];
                    Authorization.uri = Authorization.SERVER_PATH + "/authorize" + "?client_id="
                            + Authorization.CLIENT_ID + "&redirect_uri=" + Authorization.REDIRECT_URI + "&response_type=code";
                    break;
                case "-resource":
                    SpotifySet.API_SERVER_PATH = args[i + 1];
                    break;
                case "-page":
                    View.elements = Integer.parseInt(args[i + 1]);
                    break;
            }
        }

        Scanner scanner = new Scanner(System.in);

        access = false;
        while(true) {
            String in = scanner.nextLine();
            executeAction(in);
        }
    }

    public static boolean executeAction(String order){

        if (!order.equals("auth") && !order.equals("exit") && !access) {
            System.out.println("Please, provide access for application.");
            return true;
        }

        String[] order_arr = order.split(" ");


        switch(order_arr[0]){
            case "auth":
                try{
                    Authorization.GetAccessCode();
                    Authorization.GetAccessToken();
                    access = true;
                }catch(IOException | InterruptedException e){
                    System.out.println("Failed to careate server: " + e.getMessage());
                    System.out.println("---FAILED---");
                }
                break;
            case "featured":
                try {
                    features = new Features();
                }catch (IOException | InterruptedException | NoSuchElementException e){
                    break;
                }
                view.createView(features.getObjects());
                view.print("");
                break;
            case "new":
                try{
                    newReleases = new NewReleases();
                }catch (IOException | InterruptedException | NoSuchElementException e){
                    break;
                }
                view.createView(newReleases.getObjects());
                view.print("");
                break;
            case "categories":
                try {
                    categories = new Categories();
                }catch (IOException | InterruptedException | NoSuchElementException e){
                    break;
                }
                view.createView(categories.getObjects());
                view.print("");
                break;
            case "playlists":
                String category = "";
                for (int i = 1; i < order_arr.length; i++)
                    category += order_arr[i] + " ";
                try{
                    playlists = new Playlists(category.substring(0, category.length() - 1), categories);
                }catch (IOException | InterruptedException | NoSuchElementException e){
                    break;
                }
                view.createView(playlists.getObjects());
                view.print("");
                break;
            case "next":
                view.print("next");
                break;
            case "prev":
                view.print("prev");
                break;
            case "exit":
                return false;
            default:
                System.out.println("No such an option!");
        }
        return true;
    }

}
