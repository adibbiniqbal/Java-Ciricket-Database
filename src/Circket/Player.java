package Circket;

public class Player {
    private String Name;
    private String Country;
    private int Age;
    private double Height;
    private String Club;
    private String Position;
    private int Number;
    private int WeeklySalary;

    public Player(String Name, String Country, int Age, double Height, String Club, String Position, int Number, int WeeklySalary) {
        this.Name = Name;
        this.Country = Country;
        this.Age = Age;
        this.Height = Height;
        this.Club = Club;
        this.Position = Position;
        this.Number = Number;
        this.WeeklySalary = WeeklySalary;
    }


    public String getName() {
        return Name;
    }


    public String getCountry() {
        return Country;
    }

    public int getAge() {
        return Age;
    }

    public double getHeight() {
        return Height;
    }

    public String getClub() {
        return Club;
    }

    public String getPosition() {
        return Position;
    }

    public int getNumber() {
        return Number;
    }

    public int getWeeklySalary() {
        return WeeklySalary;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public void setAge(int age) {
        Age = age;
    }

    public void setHeight(double height) {
        Height = height;
    }

    public void setClub(String club) {
        Club = club;
    }

    public void setPosition(String position) {
        Position = position;
    }

    public void setNumber(int number) {
        Number = number;
    }

    public void setWeeklySalary(int weeklySalary) {
        WeeklySalary = weeklySalary;
    }

    public void display() {
        System.out.printf("Name: %s, Country: %s, Age: %d, Height: %.2f meters, Club: %s, Position: %s, Jersey Number: %s, Weekly Salary: %d\n",
                Name,
                Country,
                Age,
                Height,
                Club,
                Position,
                Number == -1 ? "N/A" : Number,
                WeeklySalary);
    }

}
