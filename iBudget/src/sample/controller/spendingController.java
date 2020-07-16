package sample.controller;

import com.jfoenix.controls.JFXRadioButton;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import sample.Database.DatabaseHandler;

public class spendingController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private LineChart<String, Double> lineChart;

    @FXML
    private PieChart pieChart;

    @FXML
    private JFXRadioButton yearRadio;

    @FXML
    private JFXRadioButton weekRadio;

    @FXML
    private ToggleGroup toggleGroup;

    @FXML
    private JFXRadioButton monthRadio;

    @FXML
    private JFXRadioButton allTimeRadio;

    @FXML
    private Label amountSpentLabel;


    private DatabaseHandler databaseHandler = new DatabaseHandler();

    @FXML
    void initialize() throws SQLException {

        lineChart.getXAxis().setTickLabelsVisible(false);
        lineChart.getXAxis().setTickMarkVisible(false);

        setWeekLineChart();
        setWeekPieChart();

        weekRadio.setOnAction(event -> {
            lineChart.getData().clear();
            pieChart.getData().clear();
            try {
                setWeekLineChart();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                setWeekPieChart();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        monthRadio.setOnAction(event -> {
            lineChart.getData().clear();
            pieChart.getData().clear();
            try {
                setMonthLineChart();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                setMonthPieChart();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        allTimeRadio.setOnAction(event -> {
            lineChart.getData().clear();
            pieChart.getData().clear();
            try {
                setAllTimeLineChart();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                setAllTimePieChart();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        yearRadio.setOnAction(event -> {
            lineChart.getData().clear();
            pieChart.getData().clear();
            try {
                setYearLineChart();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                setYearPieChart();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    private void setWeekPieChart() throws SQLException {

        ResultSet rs = databaseHandler.oneWeekPieChart(navbarController.userid);

        while (rs.next()){
            PieChart.Data n = new PieChart.Data(rs.getString("expenseDescription"),rs.getDouble("sum(expenseAmount)"));
            pieChart.getData().add(n);
        }

    }

    private void setWeekLineChart() throws SQLException {

        ResultSet rs = databaseHandler.getIncomeOneWeekLineChart(navbarController.userid);
        XYChart.Series ds = new XYChart.Series<>();
        int i = 0;
        while (rs.next()){
            ds.getData().add(new XYChart.Data(Integer.toString(i),rs.getDouble("incomeAmount")));
            i++;
        }
        lineChart.getData().add(ds);

        rs = databaseHandler.getExpenseOneWeekLineChart(navbarController.userid);
        XYChart.Series ds2 = new XYChart.Series<>();
        i = 0;
        while (rs.next()){
            ds2.getData().add(new XYChart.Data(Integer.toString(i),rs.getDouble("expenseAmount")));
            i++;
        }
        lineChart.getData().add(ds2);

        amountSpentLabel.setText(String.format("$%.02f",databaseHandler.getAmountSpentOneWeek(navbarController.userid)));

    }

    private void setMonthPieChart() throws SQLException {
        ResultSet rs = databaseHandler.oneMonthPieChart(navbarController.userid);

        while (rs.next()){
            PieChart.Data n = new PieChart.Data(rs.getString("expenseDescription"),rs.getDouble("sum(expenseAmount)"));
            pieChart.getData().add(n);
        }
    }

    private void setMonthLineChart() throws SQLException {

        ResultSet rs = databaseHandler.getIncomeOneMonthLineChart(navbarController.userid);
        XYChart.Series ds = new XYChart.Series<>();
        int i = 0;
        while (rs.next()){
            ds.getData().add(new XYChart.Data(Integer.toString(i),rs.getDouble("incomeAmount")));
            i++;
        }
        lineChart.getData().add(ds);

        rs = databaseHandler.getExpenseOneMonthLineChart(navbarController.userid);
        XYChart.Series ds2 = new XYChart.Series<>();
        i = 0;
        while (rs.next()){
            ds2.getData().add(new XYChart.Data(Integer.toString(i),rs.getDouble("expenseAmount")));
            i++;
        }
        lineChart.getData().add(ds2);

        amountSpentLabel.setText(String.format("$%.02f",databaseHandler.getAmountSpentOneMonth(navbarController.userid)));

    }

    private void setYearPieChart() throws SQLException {
        ResultSet rs = databaseHandler.oneYearPieChart(navbarController.userid);

        while (rs.next()){
            PieChart.Data n = new PieChart.Data(rs.getString("expenseDescription"),rs.getDouble("sum(expenseAmount)"));
            pieChart.getData().add(n);
        }

    }

    private void setYearLineChart() throws SQLException {

        ResultSet rs = databaseHandler.getIncomeOneYearLineChart(navbarController.userid);
        XYChart.Series ds = new XYChart.Series<>();
        int i = 0;
        while (rs.next()){
            ds.getData().add(new XYChart.Data(Integer.toString(i),rs.getDouble("incomeAmount")));
            i++;
        }
        lineChart.getData().add(ds);

        rs = databaseHandler.getExpenseOneYearLineChart(navbarController.userid);
        XYChart.Series ds2 = new XYChart.Series<>();
        i = 0;
        while (rs.next()){
            ds2.getData().add(new XYChart.Data(Integer.toString(i),rs.getDouble("expenseAmount")));
            i++;
        }
        lineChart.getData().add(ds2);

        amountSpentLabel.setText(String.format("$%.02f",databaseHandler.getAmountSpentOneYear(navbarController.userid)));
    }

    private void setAllTimeLineChart() throws SQLException {

        ResultSet rs = databaseHandler.getIncomeAllTimeLineChart(navbarController.userid);
        XYChart.Series ds = new XYChart.Series<>();
        int i = 0;
        while (rs.next()){
            ds.getData().add(new XYChart.Data(Integer.toString(i),rs.getDouble("incomeAmount")));
            i++;
        }
        lineChart.getData().add(ds);

        rs = databaseHandler.getExpenseAllTimeLineChart(navbarController.userid);
        XYChart.Series ds2 = new XYChart.Series<>();
        i = 0;
        while (rs.next()){
            ds2.getData().add(new XYChart.Data(Integer.toString(i),rs.getDouble("expenseAmount")));
            i++;
        }
        lineChart.getData().add(ds2);

        amountSpentLabel.setText(String.format("$%.02f",databaseHandler.getAmountSpentAllTime(navbarController.userid)));

    }

    private void setAllTimePieChart() throws SQLException {
        ResultSet rs = databaseHandler.allTimePieChart(navbarController.userid);

        while (rs.next()){
            PieChart.Data n = new PieChart.Data(rs.getString("expenseDescription"),rs.getDouble("sum(expenseAmount)"));
            pieChart.getData().add(n);
        }
    }
}

