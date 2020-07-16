package sample.model;

public class User {

    private int userid;
    private String username;
    private String password;
    private double salary;
    private double incomeGoal;

    public User() {
    }

    public User(String username, String password, double salary) {
        this.username = username;
        this.password = password;
        this.salary = salary;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getIncomeGoal() {
        return incomeGoal;
    }

    public void setIncomeGoal(double incomeGoal) {
        this.incomeGoal = incomeGoal;
    }
}
