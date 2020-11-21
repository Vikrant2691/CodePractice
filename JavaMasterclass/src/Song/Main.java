package Song;

import java.util.List;
import java.util.LinkedList;

public class Main {

    public static void main(String[] args) {

        LinkedList<Song> playList= new LinkedList<Song>();


        Album album = new Album("Music to be Murdered by", "Eminem");

        album.addSong(Song.createSong("Premonition","2.53"));
        album.addSong(Song.createSong("Unaccommodating","3.36"));
        album.addSong(Song.createSong("You Gon' Learn","3.54"));
        album.addSong(Song.createSong("Alfred","0.30"));
        album.addSong(Song.createSong("Godzilla","3.30"));
        album.addSong(Song.createSong("Darkness","2.53"));

        album.addToPlaylist("Premonition",playList);
        Album album1 = new Album("Music to be Murdered by", "Eminem");

        album1.addSong(Song.createSong("Premonition","2.53"));
        album1.addSong(Song.createSong("Unaccommodating","3.36"));
        album1.addSong(Song.createSong("You Gon' Learn","3.54"));
        album1.addSong(Song.createSong("Alfred","0.30"));
        album1.addSong(Song.createSong("Godzilla","3.30"));
        album1.addSong(Song.createSong("Darkness","2.53"));

        album1.addToPlaylist("Unaccommodating",playList);

        for (int i=0;i<playList.size();i++){
            System.out.println(playList.get(i).getTitle());
        }

    }
}
