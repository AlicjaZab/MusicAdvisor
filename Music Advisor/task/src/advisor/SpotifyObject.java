package advisor;

import java.util.ArrayList;

/**
 * Represents one object in set which can be received in server response
 */
public abstract class SpotifyObject {
    String name;

    SpotifyObject(String name){
        this.name = name;
    }

    @Override
    public abstract String toString();
}


class Category extends  SpotifyObject{

    String id;

    Category(String id, String name) {
        super(name);
        this.id = id;
    }

    @Override
    public String toString(){
        return name;
    }
}


class Featured extends SpotifyObject {
    String ext_url;

    Featured(String name, String ext_url){
        super(name);
        this.ext_url = ext_url;
    }

    @Override
    public String toString(){
        return name + "\n" + ext_url + "\n";
    }
}


class Playlist extends SpotifyObject {
    String ext_url;

    Playlist(String name, String ext_url){
        super(name);
        this.ext_url = ext_url;
    }

    @Override
    public String toString(){
        return name + "\n" + ext_url + "\n";
    }
}


class NewRelease extends SpotifyObject {
    String ext_url;
    ArrayList<String> artists;

    NewRelease(String name, String ext_url, ArrayList<String> artists){
        super(name);
        this.ext_url = ext_url;
        this.artists = new ArrayList<>();
        for(String artist : artists) {
            this.artists.add(artist);
        }
    }

    @Override
    public String toString(){
        return name + "\n" + artists + "\n" + ext_url + "\n";
    }
}