package generics.challenge;

import java.util.ArrayList;


public class Team<T extends Players> {

    private String name;
    private ArrayList<Players> players;

    public Team(String name) {
        this.name = name;
        this.players = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Players> getPlayers() {
        return players;
    }

    public boolean addPlayer(Players player) {
        if (!findPlayer(player)) {
            this.players.add(player);
            System.out.println("Player added");
            return true;
        } else {
            System.out.println("Player already Present");
            return false;
        }
    }

    private boolean findPlayer(Players player) {
        for (int i = 0; i < this.players.size(); i++) {
            if (this.players.get(i).getName() == player.getName()) {
                return true;
            }
        }
        return false;
    }

}
