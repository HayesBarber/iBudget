package sample.controller;

import com.jfoenix.controls.JFXButton;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import sample.Database.DatabaseHandler;
import sample.animations.Shaker;

public class homeController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label yearIncomeLabel;

    @FXML
    private Label monthIncomeLabel;

    @FXML
    private Label weekIncomeLabel;

    @FXML
    private JFXButton editIncomeButton;

    @FXML
    private Label yearIncomeTaxLabel;

    @FXML
    private Label monthIncomeTaxLabel;

    @FXML
    private Label weekIncomeTaxLabel;

    @FXML
    private ProgressIndicator saveGoalProgressBar;

    @FXML
    private JFXButton editSaveGoalButton;

    @FXML
    private AnchorPane incomePane;

    @FXML
    private AnchorPane savingGoalPane;

    @FXML
    private AnchorPane editSalaryPane;

    @FXML
    private JFXTextField salaryTextfield;

    @FXML
    private JFXButton cancelButton;

    @FXML
    private JFXButton saveButton;

    @FXML
    private AnchorPane editSaveGoalPane;

    @FXML
    private JFXTextField savingGoalTextfield;

    @FXML
    private JFXButton cancelSaveGoalButton;

    @FXML
    private JFXButton saveSaveGoalButton;

    @FXML
    private Label savingGoalLabel;

    @FXML
    private Label balanceLabel;

    @FXML
    private Label goalLabel;


    private DatabaseHandler databaseHandler = new DatabaseHandler();

    @FXML
    void initialize() {

        setIncomeLabels();

        setSaveGoalValues();

        saveSaveGoalButton.setOnAction(event -> {
            editSaveGoal();
        });

        saveButton.setOnAction(event -> {
            editSalary();
        });

        editIncomeButton.setOnAction(event -> {
            editSalaryPane.setVisible(true);
        });

        cancelButton.setOnAction(event -> {
            editSalaryPane.setVisible(false);
        });

        editSaveGoalButton.setOnAction(event -> {
            editSaveGoalPane.setVisible(true);
        });

        cancelSaveGoalButton.setOnAction(event -> {
            editSaveGoalPane.setVisible(false);
        });

    }

    private void editSaveGoal() {
        String saveGoal = savingGoalTextfield.getText().trim();

        if (isDouble(saveGoal) && saveGoal.length() > 0){
            double saveNum = Double.parseDouble(saveGoal);

            databaseHandler.updateSaveGoal(navbarController.userid, saveNum);

            setSaveGoalValues();

            savingGoalTextfield.setText("");

            editSaveGoalPane.setVisible(false);
        } else {
            Shaker shaker = new Shaker(savingGoalTextfield);
            shaker.shake();
        }

    }

    private void setSaveGoalValues() {
        double incomeGoal = databaseHandler.getSaveGoal(navbarController.userid);
        double balance = databaseHandler.getAccountBalance(navbarController.userid);

        balanceLabel.setText(String.format("$%.0f",balance));
        goalLabel.setText(String.format("$%.0f",incomeGoal));

        if (balance >= 0 && balance < incomeGoal){
            saveGoalProgressBar.setProgress(balance/incomeGoal);
            savingGoalLabel.setTextFill(Color.WHITE);
            savingGoalLabel.setText(String.format("%.0f",(balance/incomeGoal)*100) +"%");
        }else if(balance >= incomeGoal){
            saveGoalProgressBar.setProgress(1);
            savingGoalLabel.setTextFill(Color.valueOf("#00e676"));
            savingGoalLabel.setText(100 + "%");
        }else {
            saveGoalProgressBar.setProgress(0);
            savingGoalLabel.setTextFill(Color.WHITE);
            savingGoalLabel.setText(0 + "%");
        }

    }

    private void editSalary() {
        String salary = salaryTextfield.getText().trim();

        if (isDouble(salary) && salary.length() > 0){
            double salaryNum = Double.parseDouble(salary);

            databaseHandler.updateSalary(navbarController.userid, salaryNum);

            setIncomeLabels();

            salaryTextfield.setText("");

            editSalaryPane.setVisible(false);
        }else {
            Shaker shaker = new Shaker(salaryTextfield);
            shaker.shake();
        }

    }

    public boolean isDouble(String num){
        try{
            double n = Double.parseDouble(num);
            return true;
        } catch (NumberFormatException e){
            return false;
        }

    }

    private void setIncomeLabels(){

        double salary = databaseHandler.getSalary(navbarController.userid);
        double taxRate = 0.73;

        yearIncomeLabel.setText(String.format("$%.0f",salary));
        monthIncomeLabel.setText(String.format("$%.02f",salary/12));
        weekIncomeLabel.setText(String.format("$%.02f",salary/52));

        yearIncomeTaxLabel.setText(String.format("$%.0f",salary * taxRate));
        monthIncomeTaxLabel.setText(String.format("$%.02f",(salary/12) * taxRate));
        weekIncomeTaxLabel.setText(String.format("$%.02f",(salary/52) * taxRate));
    }
}

