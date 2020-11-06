package advisor;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {

    static View view = new View();
    static Categories categories;
    static Features features;
    static NewReleases newReleases;
    static Playlists playlists;

    /**
     * Interprets program arguments and executes loop user input - execute appropriate action
     * @param args - program input may have 3 arguments;
     *             access - server path (default: https://accounts.spotify.com)
     *             resource - api server path (default: https://api.spotify.com)
     *             page - elements of output to be displayed on one page (default: 5)
     */
    public static void main(String[] args) {

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
                    try {
                        View.elements = Integer.parseInt(args[i + 1]);
                    }catch (NumberFormatException e){
                        System.out.println("Invalid argument: " + args[i + 1]);
                        return;
                    }
                    break;
                default:
                    System.out.println("Invalid argument: " + args[i]);
                    return;
            }
        }

        Scanner scanner = new Scanner(System.in);

        while(true) {
            String in = scanner.nextLine();
            if (!executeAction(in)) return;
        }
    }


    /**
     * Executes one of available actions depending on given argument(s) - order
     * @param order - e.g. "fetaured", "new", "exit" etc.
     * @return flase if user chose to exit application, true otherwise
     */
    public static boolean executeAction(String order){

        if (!order.equals("auth") && !order.equals("exit") && Authorization.ACCESS_TOKEN.equals("")) {
            System.out.println("Please, provide access for application.");
            return true;
        }

        String[] order_arr = order.split(" ");


        switch(order_arr[0]){
            case "auth":
                try{
                    Authorization.GetAccessCode();
                    Authorization.GetAccessToken();
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
