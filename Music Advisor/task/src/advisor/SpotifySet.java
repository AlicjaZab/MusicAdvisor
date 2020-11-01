package advisor;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public abstract class  SpotifySet <T extends SpotifyObject>{
    public static String API_SERVER_PATH = "https://api.spotify.com";

    String URI = "";
    String jsonObjectName = "";

    ArrayList<T> objects;

    SpotifySet() {
        objects = new ArrayList<>();
    }

    public ArrayList<T> getObjects(){
        return objects;
    }

    void create() throws IOException, InterruptedException, NoSuchElementException{

            HttpRequest request = HttpRequest.newBuilder()
                    .header("Authorization", "Bearer " + Authorization.ACCESS_TOKEN)
                    .uri(java.net.URI.create(API_SERVER_PATH + URI))
                    .GET()
                    .build();

            HttpClient client = HttpClient.newBuilder().build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JsonObject jo = JsonParser.parseString(response.body()).getAsJsonObject();
            JsonObject jo_feat = jo.getAsJsonObject(jsonObjectName);

            if (jo_feat == null) {
                JsonObject error = jo.getAsJsonObject("error");
                String message = error.get("message").getAsString();
                System.out.println(message);
                throw new NoSuchElementException();
            }

            initialize(jo_feat);

    }

    abstract void initialize(JsonObject jo);

    public void addObject(T object){
        objects.add(object);
    }
}


class Categories extends SpotifySet<Category>{

    Categories() throws IOException, InterruptedException, NoSuchElementException{
        super();
        URI = "/v1/browse/categories";
        jsonObjectName = "categories";
        create();
    }


    public String getIdByName(String name) {
        if(objects == null || objects.isEmpty())
            return null;
        for(Category c : objects) {
            if(c.name.equals(name)){
                return c.id;
            }
        }
        return null;
    }

    @Override
    void initialize(JsonObject jo){
        for (JsonElement category : jo.getAsJsonArray("items")) {
            String id = category.getAsJsonObject().get("id").getAsString();
            String name = category.getAsJsonObject().get("name").getAsString();
            addObject(new Category(id, name));
        }
    }

}


class Features extends SpotifySet<Featured> {

    public Features() throws IOException, InterruptedException, NoSuchElementException{
        super();
        URI = "/v1/browse/featured-playlists";
        jsonObjectName = "playlists";
        create();
    }

    @Override
    void initialize(JsonObject jo){
        for (JsonElement playlist : jo.getAsJsonArray("items")) {
            String name = playlist.getAsJsonObject().get("name").getAsString();
            JsonObject ext_url = playlist.getAsJsonObject().getAsJsonObject("external_urls");
            String url = ext_url.get("spotify").getAsString();
            addObject(new Featured(name, url));
        }
    }

}


class Playlists extends SpotifySet<Playlist> {
    public String category_name;

    Playlists(String category_name, Categories categories) throws IOException, InterruptedException, NoSuchElementException{
        super();
        if(categories == null){
            categories = new Categories();
        }
        String id = categories.getIdByName(category_name);
        if(id == null){
            System.out.println("Unknown category name.");
            throw new NoSuchElementException();
        }
        this.category_name = category_name;
        URI = "/v1/browse/categories"+ "/" + id + "/playlists";
        jsonObjectName = "playlists";
        create();
    }

    @Override
    void initialize(JsonObject jo){
        for (JsonElement playlist : jo.getAsJsonArray("items")) {
            String name = playlist.getAsJsonObject().get("name").getAsString();
            JsonObject ext_url = playlist.getAsJsonObject().getAsJsonObject("external_urls");
            String url = ext_url.get("spotify").getAsString();
            addObject(new Playlist(name, url));

        }
    }
}


class NewReleases extends SpotifySet<NewRelease> {

    NewReleases() throws IOException, InterruptedException, NoSuchElementException{
        super();
        URI = "/v1/browse/new-releases";
        jsonObjectName = "albums";
        create();
    }

    @Override
    void initialize(JsonObject jo){
        for (JsonElement album : jo.getAsJsonArray("items")) {
            String name = album.getAsJsonObject().get("name").getAsString();
            ArrayList<String> names = new ArrayList<>();
            for (JsonElement artist : album.getAsJsonObject().getAsJsonArray("artists")) {
                names.add(artist.getAsJsonObject().get("name").getAsString());
            }
            JsonObject ext_url = album.getAsJsonObject().getAsJsonObject("external_urls");
            String url = ext_url.get("spotify").getAsString();
            addObject(new NewRelease(name, url, names));
        }
    }
}
