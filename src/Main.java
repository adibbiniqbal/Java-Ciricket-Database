import Circket.PlayersDatabase;

public class Main {
    public static void main(String[] args) {
        PlayersDatabase ipl = new PlayersDatabase("players.txt");
        ipl.displayMainMenu();
    }
}