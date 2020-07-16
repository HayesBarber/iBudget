package sample.Database;

import sample.model.Expense;
import sample.model.Income;
import sample.model.User;

import javax.lang.model.element.NestingKind;
import java.sql.*;

public class DatabaseHandler extends Configs {

    Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {

        String connectionString = "jdbc:mysql://" + dbHost + ":"
                +dbPort+ "/" +dbName+
                "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString,dbUser,dbPass);

        return dbConnection;

    }

    public void signUpUser(User user){

        String query = "insert into users (username,upassword,salary,accountBalance) values (?,?,?,?);";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setDouble(3, user.getSalary());
            preparedStatement.setDouble(4, 0);

            preparedStatement.executeUpdate();

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

    public void deleteUser(int userid){

        String query = "Delete from users where userid = ?";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);

            preparedStatement.setInt(1, userid);
            preparedStatement.executeUpdate();

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

    public void deleteIncome(int userid, int incomeid){

        String query = "Delete from income where income.userid = ? and income.incomeid = ?";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);

            preparedStatement.setInt(1, userid);
            preparedStatement.setInt(2, incomeid);
            preparedStatement.executeUpdate();

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

    public void deleteExpense(int userid, int expenseid){

        String query = "Delete from expense where expense.userid = ? and expense.expenseid = ?";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);

            preparedStatement.setInt(1, userid);
            preparedStatement.setInt(2, expenseid);
            preparedStatement.executeUpdate();

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

    public boolean doesUserNameExist(String username){
        boolean exists = true;
        String query = "select * from users where users.username = ?";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);

            preparedStatement.setString(1, username);

            ResultSet rs = preparedStatement.executeQuery();

            exists = rs.next();

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return exists;

    }

    public String getUsername(int userid){
        String username = "";
        String query = "select username from users where users.userid = ?";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);

            preparedStatement.setInt(1, userid);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                username = rs.getString("username");
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return username;

    }

    public String getPassword(int userid){
        String password = "";
        String query = "select upassword from users where users.userid = ?";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);

            preparedStatement.setInt(1, userid);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                password = rs.getString("upassword");
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return password;
    }

    public void updateUsername(int userid, String username){
        String query = "update users set username = ? where users.userid = ?";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);

            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, userid);

            preparedStatement.executeUpdate();

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updatePassword(int userid, String password){
        String query = "update users set upassword = ? where users.userid = ?";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);

            preparedStatement.setString(1, password);
            preparedStatement.setInt(2, userid);

            preparedStatement.executeUpdate();

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

    public ResultSet loginUser(User user){

        ResultSet resultSet = null;

        String query = "select * from users where users.username = ? and users.upassword = ?";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());

            resultSet = preparedStatement.executeQuery();


        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet getIncomeOneYearLineChart(int userid){
        ResultSet resultSet = null;

        String query = "select incomeAmount from income where incomeDate >= DATE_SUB(CURDATE(), INTERVAL 1 YEAR) and income.userid = ?;";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            preparedStatement.setInt(1, userid);

            resultSet = preparedStatement.executeQuery();


        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet getExpenseOneYearLineChart(int userid){
        ResultSet resultSet = null;

        String query = "select expenseAmount from expense where expenseDate >= DATE_SUB(CURDATE(), INTERVAL 1 YEAR) and expense.userid = ?;";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            preparedStatement.setInt(1, userid);

            resultSet = preparedStatement.executeQuery();


        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet getExpenseOneMonthLineChart(int userid){
        ResultSet resultSet = null;

        String query = "select expenseAmount from expense where expenseDate >= DATE_SUB(CURDATE(), INTERVAL 1 MONTH) and expense.userid = ?;";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            preparedStatement.setInt(1, userid);

            resultSet = preparedStatement.executeQuery();


        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet getExpenseOneWeekLineChart(int userid){
        ResultSet resultSet = null;

        String query = "select expenseAmount from expense where expenseDate >= DATE_SUB(CURDATE(), INTERVAL 1 WEEK) and expense.userid = ?;";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            preparedStatement.setInt(1, userid);

            resultSet = preparedStatement.executeQuery();


        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet getExpenseAllTimeLineChart(int userid){
        ResultSet resultSet = null;

        String query = "select expenseAmount from expense where expense.userid = ?;";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            preparedStatement.setInt(1, userid);

            resultSet = preparedStatement.executeQuery();


        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet getIncomeOneMonthLineChart(int userid){
        ResultSet resultSet = null;

        String query = "select incomeAmount from income where incomeDate >= DATE_SUB(CURDATE(), INTERVAL 1 MONTH) and income.userid = ?;";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            preparedStatement.setInt(1, userid);

            resultSet = preparedStatement.executeQuery();


        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet getIncomeOneWeekLineChart(int userid){
        ResultSet resultSet = null;

        String query = "select incomeAmount from income where incomeDate >= DATE_SUB(CURDATE(), INTERVAL 1 WEEK) and income.userid = ?;";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            preparedStatement.setInt(1, userid);

            resultSet = preparedStatement.executeQuery();


        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet getIncomeAllTimeLineChart(int userid){
        ResultSet resultSet = null;

        String query = "select incomeAmount from income where income.userid = ?;";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            preparedStatement.setInt(1, userid);

            resultSet = preparedStatement.executeQuery();


        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return resultSet;
    }

    public double getAmountSpentOneYear(int userid){
        double amount = 0;

        String query = "select sum(expenseAmount) from expense where expenseDate >= DATE_SUB(CURDATE(), INTERVAL 1 YEAR) and expense.userid = ?;";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);

            preparedStatement.setInt(1, userid);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                amount = rs.getDouble("sum(expenseAmount)");
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return amount;


    }

    public double getAmountSpentOneWeek(int userid){
        double amount = 0;

        String query = "select sum(expenseAmount) from expense where expenseDate >= DATE_SUB(CURDATE(), INTERVAL 1 WEEK) and expense.userid = ?;";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);

            preparedStatement.setInt(1, userid);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                amount = rs.getDouble("sum(expenseAmount)");
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return amount;
    }

    public double getAmountSpentAllTime(int userid){
        double amount = 0;

        String query = "select sum(expenseAmount) from expense where expense.userid = ?;";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);

            preparedStatement.setInt(1, userid);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                amount = rs.getDouble("sum(expenseAmount)");
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return amount;
    }

    public double getAmountSpentOneMonth(int userid){
        double amount = 0;

        String query = "select sum(expenseAmount) from expense where expenseDate >= DATE_SUB(CURDATE(), INTERVAL 1 MONTH) and expense.userid = ?;";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);

            preparedStatement.setInt(1, userid);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                amount = rs.getDouble("sum(expenseAmount)");
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return amount;
    }

    public ResultSet getIncomeList(int userid){

        ResultSet resultSet = null;
        String query = "select * from income where income.userid = ? order by income.incomeid desc";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            preparedStatement.setInt(1, userid);

            resultSet = preparedStatement.executeQuery();


        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return resultSet;

    }

    public ResultSet getExpenseList(int userid){
        ResultSet resultSet = null;
        String query = "select * from expense where expense.userid = ? order by expense.expenseid desc";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            preparedStatement.setInt(1, userid);

            resultSet = preparedStatement.executeQuery();


        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return resultSet;
    }


    public double getSalary(int userid){
        double salary = 0;
        String query = "select salary from users where users.userid = ?";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);

            preparedStatement.setInt(1, userid);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                salary = rs.getDouble("salary");
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return salary;

    }

    public double getSaveGoal(int userid){
        double saveGoal = 0;
        String query = "select incomeGoal from users where users.userid = ?";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);

            preparedStatement.setInt(1, userid);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs == null) return 0;

            while (rs.next()){
                saveGoal = rs.getDouble("incomeGoal");
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return saveGoal;
    }

    public void updateSalary(int userid, double salary){
        String query = "update users set salary = ? where users.userid = ?";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);

            preparedStatement.setDouble(1, salary);
            preparedStatement.setInt(2, userid);

            preparedStatement.executeUpdate();

        }catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }


    }

    public void updateSaveGoal(int userid, double incomeGoal){

        String query = "update users set incomeGoal = ? where users.userid = ?";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);

            preparedStatement.setDouble(1, incomeGoal);
            preparedStatement.setInt(2, userid);

            preparedStatement.executeUpdate();

        }catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

    public void addIncome(int userid, Income income){

        String query = "Insert into income (incomeAmount,incomeDescription,incomeDate,userid) values (?,?,CURDATE(),?);";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);

            preparedStatement.setDouble(1, income.getIncomeAmount());
            preparedStatement.setString(2,income.getIncomeDescription());
            preparedStatement.setInt(3, userid);

            preparedStatement.executeUpdate();

        }catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        query = "update users set accountBalance = accountBalance + ? where users.userid = ?";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);

            preparedStatement.setDouble(1, income.getIncomeAmount());
            preparedStatement.setInt(2, userid);

            preparedStatement.executeUpdate();

        }catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

    public void addExpense(int userid, Expense expense){

        String query = "Insert into expense (expenseAmount,expenseDescription,expenseDate,userid) values (?,?,CURDATE(),?);";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);

            preparedStatement.setDouble(1, expense.getExpenseAmount());
            preparedStatement.setString(2,expense.getExpenseDescription());
            preparedStatement.setInt(3, userid);

            preparedStatement.executeUpdate();

        }catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        query = "update users set accountBalance = accountBalance - ? where users.userid = ?";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);

            preparedStatement.setDouble(1, expense.getExpenseAmount());
            preparedStatement.setInt(2, userid);

            preparedStatement.executeUpdate();

        }catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

    public double getAccountBalance(int userid){

        double balance = 0;
        String query = "select accountBalance from users where users.userid = ?";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);

            preparedStatement.setInt(1, userid);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                balance = rs.getDouble("accountBalance");
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return balance;

    }

    public ResultSet oneWeekPieChart(int userid){
        ResultSet resultSet = null;
        String query ="select expenseDescription, sum(expenseAmount) from expense where expenseDate >= DATE_SUB(CURDATE(),INTERVAL 1 WEEK) and expense.userid = ? group by expenseDescription;";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            preparedStatement.setInt(1, userid);

            resultSet = preparedStatement.executeQuery();


        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet oneMonthPieChart(int userid){
        ResultSet resultSet = null;
        String query ="select expenseDescription, sum(expenseAmount) from expense where expenseDate >= DATE_SUB(CURDATE(),INTERVAL 1 MONTH) and expense.userid = ? group by expenseDescription;";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            preparedStatement.setInt(1, userid);

            resultSet = preparedStatement.executeQuery();


        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet oneYearPieChart(int userid){
        ResultSet resultSet = null;

        String query ="select expenseDescription, sum(expenseAmount) from expense where expenseDate >= DATE_SUB(CURDATE(),INTERVAL 1 YEAR) and expense.userid = ? group by expenseDescription;";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            preparedStatement.setInt(1, userid);

            resultSet = preparedStatement.executeQuery();


        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet allTimePieChart(int userid){
        ResultSet resultSet = null;

        String query ="select expenseDescription, sum(expenseAmount) from expense where expense.userid = ? group by expenseDescription;";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            preparedStatement.setInt(1, userid);

            resultSet = preparedStatement.executeQuery();


        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return resultSet;
    }
}
