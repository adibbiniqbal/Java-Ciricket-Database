package Circket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

public class PlayersDatabase {
    private final List<Player> playerList = new ArrayList<Player>();
    private final Map<String, Integer> countryPlayerCount = new HashMap<String, Integer>();
    private final String FILE_NAME;

    public PlayersDatabase(String FILE_NAME) {
        this.FILE_NAME = FILE_NAME;
        loadPlayers();
    }

    private void loadPlayers() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));
            String line;
            while (true) {
                line = reader.readLine();
                if (line == null) {
                    System.out.println("Successfully loaded " + playerList.size() + " players from " + FILE_NAME);
                    reader.close();
                    return;
                }
                String[] playerInfo = line.split(",");
                String name = playerInfo[0].trim();
                String country = playerInfo[1].trim();
                int age = Integer.parseInt(playerInfo[2]);
                double height = Double.parseDouble(playerInfo[3]);
                String club = playerInfo[4].trim();
                String position = playerInfo[5].trim();
                int number = playerInfo[6].isEmpty() ? -1 : Integer.parseInt(playerInfo[6]);
                int weeklySalary = Integer.parseInt(playerInfo[7]);
                countryPlayerCount.put(country.trim().toLowerCase(), countryPlayerCount.getOrDefault(country.trim().toLowerCase(), 0) + 1);
                playerList.add(new Player(name, country, age, height, club, position, number, weeklySalary));
            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    private void savePlayers() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME));
            for (Player player : playerList) {
                writer.write(String.format("%s,%s,%d,%.2f,%s,%s,%s,%d\n",
                        player.getName(),
                        player.getCountry(),
                        player.getAge(),
                        player.getHeight(),
                        player.getClub(),
                        player.getPosition(),
                        player.getNumber() == -1 ? "" : player.getNumber(),
                        player.getWeeklySalary()));
            }
            System.out.println("Successfully saved " + playerList.size() + " players to " + FILE_NAME);
            writer.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void addPlayer(Scanner scanner) {
        System.out.print("Enter player name: ");
        String name = scanner.nextLine().trim();
        for (Player player : playerList) {
            if (player.getName().equalsIgnoreCase(name)) {
                System.out.println("Player already exists!");
                return;
            }
        }
        System.out.print("Enter player country: ");
        String country = scanner.nextLine().trim();
        System.out.print("Enter player age: ");
        int age = scanner.nextInt();
        System.out.print("Enter player height(in meters): ");
        double height = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter player club: ");
        String club = scanner.nextLine().trim();
        String position;
        while (true) {
            System.out.print("Enter player position: ");
            position = scanner.nextLine().trim();
            if (position.equalsIgnoreCase("Batsman") || position.equalsIgnoreCase("Bowler") || position.equalsIgnoreCase("Wicketkeeper") || position.equalsIgnoreCase("Allrounder")) {
                break;
            } else {
                System.out.println("Invalid Position");
            }
        }
        int number = -1;
        while (true) {
            System.out.print("Enter player number: ");
            String preNumber = scanner.nextLine().trim();
            if (preNumber.isEmpty() || preNumber.equalsIgnoreCase("-1")) {
                break;
            } else if (preNumber.matches("[0-9]*")) {
                number = Integer.parseInt(preNumber);
                break;
            } else {
                System.out.println("Invalid number");
            }
        }

        System.out.print("Enter player weekly salary: ");
        int weeklySalary = scanner.nextInt();
        scanner.nextLine();
        countryPlayerCount.put(country.trim().toLowerCase(), countryPlayerCount.getOrDefault(country.trim().toLowerCase(), 0) + 1);
        playerList.add(new Player(name, country, age, height, club, position, number, weeklySalary));
        System.out.println("Player added successfully!");
    }

    private void displayPlayers(List<Player> Players) {
        if (Players.isEmpty()) {
            System.out.println("No such player exists!");
        }
        for (Player player : Players) {
            player.display();
        }
        System.out.println();
    }

    private List<Player> searchName(Scanner scanner) {
        System.out.print("Please enter the name of the player: ");
        String name = scanner.nextLine();
        List<Player> result = new ArrayList<Player>();
        for (Player player : playerList) {
            if (name.equalsIgnoreCase(player.getName())) {
                result.add(player);
            }
        }
        return result;
    }

    private List<Player> searchClubCountry(Scanner scanner) {
        System.out.print("Please enter the country: ");
        String country = scanner.nextLine();
        System.out.print("Please enter the club or enter 'ANY' to display all players of the country: ");
        String club = scanner.nextLine();
        List<Player> result = new ArrayList<Player>();
        for (Player player : playerList) {
            if (country.equalsIgnoreCase(player.getCountry())
                    && (club.equalsIgnoreCase(player.getClub()) || club.equalsIgnoreCase("ANY"))) {
                result.add(player);
            }
        }
        return result;
    }

    private List<Player> searchPosition(Scanner scanner) {
        String position;
        while (true) {
            System.out.print("Please enter the position: ");
            position = scanner.nextLine().trim();
            if (position.equalsIgnoreCase("Batsman") || position.equalsIgnoreCase("Bowler") || position.equalsIgnoreCase("Wicketkeeper") || position.equalsIgnoreCase("Allrounder")) {
                break;
            } else {
                System.out.println("Invalid Position");
            }
        }
        List<Player> result = new ArrayList<Player>();
        for (Player player : playerList) {
            if (position.equalsIgnoreCase(player.getPosition())) {
                result.add(player);
            }
        }
        return result;
    }

    private List<Player> searchSalaryRange(Scanner scanner) {
        System.out.println("Search weekly salary range");
        System.out.print("Enter minimum of the range: ");
        int min = scanner.nextInt();
        System.out.print("Enter maximum of the range: ");
        int max = scanner.nextInt();
        scanner.nextLine();
        List<Player> result = new ArrayList<Player>();
        for (Player player : playerList) {
            double salary = player.getWeeklySalary();
            if (salary >= min && salary <= max) {
                result.add(player);
            }
        }
        return result;
    }

    private void countryPlayers(Scanner scanner) {
        System.out.println("\nCountry-wise Player Count:");
        for (Map.Entry<String, Integer> entry : countryPlayerCount.entrySet()) {
            System.out.println("Country: " + (entry.getKey().substring(0, 1).toUpperCase() + entry.getKey().substring(1).toLowerCase()) + ", Count: " + entry.getValue());
        }
    }

    private List<Player> maximumSalary(Scanner scanner) {
        System.out.print("Enter club: ");
        String club = scanner.nextLine();
        double maxSalary = -1;
        List<Player> result = new ArrayList<Player>();
        for (Player player : playerList) {
            if (club.equalsIgnoreCase(player.getClub()) && (maxSalary < player.getWeeklySalary())) {
                maxSalary = player.getWeeklySalary();
            }
        }
        if (maxSalary == -1) {
            return result;
        }
        for (Player player : playerList) {
            if (club.equalsIgnoreCase(player.getClub()) && (maxSalary == player.getWeeklySalary())) {
                result.add(player);
            }
        }
        return result;
    }

    private List<Player> maximumAge(Scanner scanner) {
        System.out.print("Enter club: ");
        String club = scanner.nextLine();
        int maxAge = -1;
        List<Player> result = new ArrayList<Player>();
        for (Player player : playerList) {
            if (club.equalsIgnoreCase(player.getClub()) && (maxAge < player.getAge())) {
                maxAge = player.getAge();
            }
        }
        if (maxAge == -1) {
            return result;
        }
        for (Player player : playerList) {
            if (club.equalsIgnoreCase(player.getClub()) && (maxAge == player.getAge())) {
                result.add(player);
            }
        }
        return result;
    }

    private List<Player> maximumHeight(Scanner scanner) {
        System.out.print("Enter club: ");
        String club = scanner.nextLine();
        double maxHeight = -1;
        List<Player> result = new ArrayList<Player>();
        for (Player player : playerList) {
            if (club.equalsIgnoreCase(player.getClub()) && (maxHeight < player.getHeight())) {
                maxHeight = player.getHeight();
            }
        }
        if (maxHeight == -1) {
            return result;
        }
        for (Player player : playerList) {
            if (club.equalsIgnoreCase(player.getClub()) && (maxHeight == player.getHeight())) {
                result.add(player);
            }
        }
        return result;
    }

    void totalClubSalary(Scanner scanner) {
        System.out.print("Enter club: ");
        String club = scanner.nextLine();
        double totalSalary = 0;
        for (Player player : playerList) {
            if (club.equalsIgnoreCase(player.getClub())) {
                totalSalary = totalSalary + player.getWeeklySalary();
            }
        }
        if (totalSalary == 0) {
            System.out.println("No such club with this name");
        } else {
            System.out.printf("Total weekly salary: %.0f\n", totalSalary);
        }
    }

    private void searchPlayer(Scanner sc) {
        while (true) {
            System.out.println("\nPlayer Searching Options:");
            System.out.println("(1) By Player Name");
            System.out.println("(2) By Club and  Country");
            System.out.println("(3) By Position");
            System.out.println("(4) By Salary Range");
            System.out.println("(5) Country-Wise Player Count");
            System.out.println("(6) Back to Main Menu\n");
            System.out.print("Enter your choice: ");
            String str = sc.nextLine();
            int choice = 0;
            try {
                choice = Integer.parseInt(str);
            } catch (Exception e) {
                System.out.println(e);
            }
            switch (choice) {
                case 1:
                    List<Player> nameSearch = searchName(sc);
                    displayPlayers(nameSearch);
                    break;
                case 2:
                    List<Player> clubCountrySearch = searchClubCountry(sc);
                    displayPlayers(clubCountrySearch);
                    break;
                case 3:
                    List<Player> positionSearch = searchPosition(sc);
                    displayPlayers(positionSearch);
                    break;
                case 4:
                    List<Player> salarySearch = searchSalaryRange(sc);
                    displayPlayers(salarySearch);
                    break;
                case 5:
                    countryPlayers(sc);
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private void searchClub(Scanner sc) {
        while (true) {
            System.out.println("\nClub Searching Options:");
            System.out.println("(1) Player(s) with maximum salary of the club");
            System.out.println("(2) Player(s) with maximum age of the club");
            System.out.println("(3) Player(s) with maximum height of the club");
            System.out.println("(4) Total salary of the club");
            System.out.println("(5) Back to Main Menu\n");
            System.out.print("Enter your choice: ");
            String str = sc.nextLine();
            int choice = 0;
            try {
                choice = Integer.parseInt(str);
            } catch (Exception e) {
                System.out.println(e);
            }
            switch (choice) {
                case 1:
                    List<Player> maxSalary = maximumSalary(sc);
                    displayPlayers(maxSalary);
                    break;
                case 2:
                    List<Player> maxAge = maximumAge(sc);
                    displayPlayers(maxAge);
                    break;
                case 3:
                    List<Player> maxHeight = maximumHeight(sc);
                    displayPlayers(maxHeight);
                    break;
                case 4:
                    totalClubSalary(sc);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    public void displayMainMenu() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\nMain Menu:");
            System.out.println("(1) Search Players");
            System.out.println("(2) Search Club");
            System.out.println("(3) Add Player");
            System.out.println("(4) Exit System\n");
            System.out.print("Enter your choice: ");
            String str = sc.nextLine();
            int choice = 0;
            try {
                choice = Integer.parseInt(str);
            } catch (Exception e) {
                System.out.println(e);
            }
            switch (choice) {
                case 1:
                    searchPlayer(sc);
                    break;
                case 2:
                    searchClub(sc);
                    break;
                case 3:
                    addPlayer(sc);
                    break;
                case 4:
                    savePlayers();
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}


