package Song;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Album {

    private String albumName;
    private String artist;
    private SongList songList;

    public Album(String albumName, String artist) {
        this.albumName = albumName;
        this.artist = artist;
        songList = new SongList();
    }


    public boolean addSong(Song song) {

        return songList.addSong(song);

    }

    public boolean addToPlaylist(String trackName, LinkedList<Song> playList){
        Song song= songList.getSong(trackName);
        if(song!=null){
            playList.add(song);
            System.out.println("Song added");
            return true;
        }
        else{
            System.out.println("Song not found");
            return false;
        }
    }

    public String getAlbumName() {
        return albumName;
    }


    class SongList {
        private List<Song> songs;

        public SongList() {
            songs = new ArrayList<>();
        }

        public boolean addSong(Song song) {

            if (!findSong(song)) {
                songs.add(song);
                System.out.println("Song added");
                return true;
            }

            return false;
        }

        public boolean findSong(Song song) {

            return this.songs.contains(song);

        }

        public Song getSong(String song) {
            for (int i = 0; i < songs.size(); i++) {
                if (songs.get(i).getTitle().equals(song))
                    return songs.get(i);
            }
            return null;
        }

    }


}
