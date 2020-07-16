package sample.controller;

import com.jfoenix.controls.*;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import sample.Database.DatabaseHandler;
import sample.animations.Shaker;
import sample.model.Expense;
import sample.model.Income;

public class walletController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private AnchorPane incomePane;

    @FXML
    private Label balanceLabel;

    @FXML
    private JFXTextField incomeAmountTextfield;

    @FXML
    private JFXComboBox<String> incomeDesciptionComboBox;

    @FXML
    private JFXButton saveIncomeButton;

    @FXML
    private JFXTextField expenseAmountTextfield;

    @FXML
    private JFXComboBox<String> expenseDescriptionComboBox;

    @FXML
    private JFXButton saveExpenseButton;

    @FXML
    private JFXListView<Income> incomeListView;

    @FXML
    private JFXListView<Expense> expenseListView;

    @FXML
    private ImageView seeMoreImageView;

    @FXML
    private Label seeMoreAmountLabel;

    @FXML
    private Label seeMoreDateLabel;

    @FXML
    private Label seeMoreAboutLabel;

    @FXML
    private JFXButton seeMoreDeleteButton;

    @FXML
    private JFXButton seeMoreCancelButton;

    @FXML
    private JFXTabPane tabPane;

    private ObservableList<Income> incomeList;
    private ObservableList<Expense> expenseList;

    private DatabaseHandler databaseHandler = new DatabaseHandler();

    private final String[] optionsIncome = {"Check","Cash","Other"};
    private final String[] optionsExpense = {"Housing", "Utilities", "Groceries" , "Fun", "Other"};

    @FXML
    void initialize() throws SQLException {

        setBalanceLabel();

        incomeDesciptionComboBox.setItems(FXCollections.observableArrayList(optionsIncome));
        expenseDescriptionComboBox.setItems(FXCollections.observableArrayList(optionsExpense));

        setIncomeHistory();

        setExpenseHistory();

        incomeListView.setOnMouseClicked(event -> {
            setIncomeSeeMore();
        });

        expenseListView.setOnMouseClicked(event -> {
            setExpenseSeeMore();
        });

        seeMoreCancelButton.setOnAction(event -> {
            cancelSeeMore();
        });

        saveIncomeButton.setOnAction(event -> {

            saveIncome();
            try {
                setIncomeHistory();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        });

        saveExpenseButton.setOnAction(event -> {

            saveExpense();
            try {
                setExpenseHistory();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        });

    }

    private void setExpenseSeeMore() {

        boolean isEmpty = expenseListView.getSelectionModel().isEmpty();

        if (!isEmpty){
            Image image;
            tabPane.setVisible(false);
            seeMoreAboutLabel.setVisible(true);
            seeMoreAmountLabel.setVisible(true);
            seeMoreCancelButton.setVisible(true);
            seeMoreDateLabel.setVisible(true);
            seeMoreDeleteButton.setVisible(true);
            seeMoreImageView.setVisible(true);

            seeMoreDateLabel.setText("Date: "+expenseListView.getSelectionModel().getSelectedItem().getExpenseDate());
            seeMoreAmountLabel.setText("Amount: "+String.format("$%.02f",expenseListView.getSelectionModel().getSelectedItem().getExpenseAmount()));
            seeMoreAmountLabel.setTextFill(Color.RED);
            seeMoreAboutLabel.setText(expenseListView.getSelectionModel().getSelectedItem().getExpenseDescription());

            String description = expenseListView.getSelectionModel().getSelectedItem().getExpenseDescription();

            switch (description) {
                case "Housing":
                    image = new Image("/sample/assets/home.png");
                    seeMoreImageView.setImage(image);
                    break;
                case "Fun":
                    image = new Image("/sample/assets/fun.png");
                    seeMoreImageView.setImage(image);
                    break;
                case "Utilities":
                    image = new Image("/sample/assets/utilites.png");
                    seeMoreImageView.setImage(image);
                    break;
                case "Groceries":
                    image = new Image("/sample/assets/groceries.png");
                    seeMoreImageView.setImage(image);
                    break;
                default:
                    image = new Image("/sample/assets/other.png");
                    seeMoreImageView.setImage(image);
                    break;
            }

            seeMoreDeleteButton.setOnAction(event -> {
                deleteExpense();
            });
        }

    }

    private void deleteExpense() {

        databaseHandler.deleteExpense(navbarController.userid, expenseListView.getSelectionModel().getSelectedItem().getExpenseid());

        try {
            setExpenseHistory();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        tabPane.setVisible(true);
        seeMoreAboutLabel.setVisible(false);
        seeMoreAmountLabel.setVisible(false);
        seeMoreCancelButton.setVisible(false);
        seeMoreDateLabel.setVisible(false);
        seeMoreDeleteButton.setVisible(false);
        seeMoreImageView.setVisible(false);

    }

    private void deleteIncome() {

        databaseHandler.deleteIncome(navbarController.userid, incomeListView.getSelectionModel().getSelectedItem().getIncomeid());

        try {
            setIncomeHistory();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        tabPane.setVisible(true);
        seeMoreAboutLabel.setVisible(false);
        seeMoreAmountLabel.setVisible(false);
        seeMoreCancelButton.setVisible(false);
        seeMoreDateLabel.setVisible(false);
        seeMoreDeleteButton.setVisible(false);
        seeMoreImageView.setVisible(false);

    }

    private void cancelSeeMore() {
        tabPane.setVisible(true);
        seeMoreAboutLabel.setVisible(false);
        seeMoreAmountLabel.setVisible(false);
        seeMoreCancelButton.setVisible(false);
        seeMoreDateLabel.setVisible(false);
        seeMoreDeleteButton.setVisible(false);
        seeMoreImageView.setVisible(false);
    }

    private void setIncomeSeeMore() {

        boolean isEmpty = incomeListView.getSelectionModel().isEmpty();

        if (!isEmpty){
            Image image;
            tabPane.setVisible(false);
            seeMoreAboutLabel.setVisible(true);
            seeMoreAmountLabel.setVisible(true);
            seeMoreCancelButton.setVisible(true);
            seeMoreDateLabel.setVisible(true);
            seeMoreDeleteButton.setVisible(true);
            seeMoreImageView.setVisible(true);

            seeMoreDateLabel.setText("Date: "+incomeListView.getSelectionModel().getSelectedItem().getIncomeDate());
            seeMoreAmountLabel.setText("Amount: "+String.format("$%.02f",incomeListView.getSelectionModel().getSelectedItem().getIncomeAmount()));
            seeMoreAmountLabel.setTextFill(Color.valueOf("#00e676"));
            seeMoreAboutLabel.setText(incomeListView.getSelectionModel().getSelectedItem().getIncomeDescription());

            String description = incomeListView.getSelectionModel().getSelectedItem().getIncomeDescription();
            switch (description) {
                case "Cash":
                    image = new Image("/sample/assets/cash.png");
                    seeMoreImageView.setImage(image);
                    break;
                case "Check":
                    image = new Image("/sample/assets/check.png");
                    seeMoreImageView.setImage(image);
                    break;
                default:
                    image = new Image("/sample/assets/other.png");
                    seeMoreImageView.setImage(image);
                    break;
            }

            seeMoreDeleteButton.setOnAction(event -> {
                deleteIncome();
            });

        }

    }

    private void setExpenseHistory() throws SQLException {

        expenseList = FXCollections.observableArrayList();

        ResultSet rs = databaseHandler.getExpenseList(navbarController.userid);

        while (rs.next()){
            Expense expense = new Expense();
            expense.setExpenseAmount(rs.getDouble("expenseAmount"));
            expense.setExpenseDate(String.valueOf(rs.getDate("expenseDate")));
            expense.setExpenseDescription(rs.getString("expenseDescription"));
            expense.setExpenseid(rs.getInt("expenseid"));

            expenseList.addAll(expense);
        }

        expenseListView.setItems(expenseList);
        expenseListView.setCellFactory(expenseCellController -> new expenseCellController());


    }

    private void setIncomeHistory() throws SQLException {

        incomeList = FXCollections.observableArrayList();

        ResultSet rs = databaseHandler.getIncomeList(navbarController.userid);

        while (rs.next()){
            Income income = new Income();
            income.setIncomeAmount(rs.getDouble("incomeAmount"));
            income.setIncomeDate(String.valueOf(rs.getDate("incomeDate")));
            income.setIncomeDescription(rs.getString("incomeDescription"));
            income.setIncomeid(rs.getInt("incomeid"));

            incomeList.addAll(income);
        }

        incomeListView.setItems(incomeList);
        incomeListView.setCellFactory(incomeCellController -> new incomeCellController());
    }

    private void setBalanceLabel(){
        double balance = databaseHandler.getAccountBalance(navbarController.userid);
        balanceLabel.setText(String.format("$%.02f",balance));
        if (balance < 0){
            balanceLabel.setTextFill(Color.RED);
        }else {
            balanceLabel.setTextFill(Color.valueOf("#00e676"));
        }
    }

    private void saveExpense() {

        String amount = expenseAmountTextfield.getText().trim();

        if (isDouble(amount) && isPositive(amount) && amount.length() > 0 && !expenseDescriptionComboBox.getSelectionModel().isEmpty()){
            double expenseAmount = Double.parseDouble(amount);
            String description = expenseDescriptionComboBox.getSelectionModel().getSelectedItem();
            Expense expense = new Expense();
            expense.setExpenseAmount(expenseAmount);
            expense.setExpenseDescription(description);

            databaseHandler.addExpense(navbarController.userid,expense);

            expenseAmountTextfield.setText("");
            expenseDescriptionComboBox.getSelectionModel().clearSelection();

            setBalanceLabel();

        } else {
            Shaker amountShaker = new Shaker(expenseAmountTextfield);
            Shaker descriptionShaker = new Shaker(expenseDescriptionComboBox);

            if (amount.length() <= 0 || !isDouble(amount) || !isPositive(amount)){
                amountShaker.shake();
            }

            if (expenseDescriptionComboBox.getSelectionModel().isEmpty()) {
                descriptionShaker.shake();
            }
        }

    }

    private void saveIncome() {

        String amount = incomeAmountTextfield.getText().trim();

        if (isDouble(amount) && isPositive(amount) && amount.length() > 0 && !incomeDesciptionComboBox.getSelectionModel().isEmpty()){
            double incomeAmount = Double.parseDouble(amount);
            String description = incomeDesciptionComboBox.getSelectionModel().getSelectedItem();
            Income income = new Income();
            income.setIncomeAmount(incomeAmount);
            income.setIncomeDescription(description);

            databaseHandler.addIncome(navbarController.userid,income);

            incomeAmountTextfield.setText("");
            incomeDesciptionComboBox.getSelectionModel().clearSelection();

            setBalanceLabel();

        } else{
            Shaker amountShaker = new Shaker(incomeAmountTextfield);
            Shaker descriptionShaker = new Shaker(incomeDesciptionComboBox);

            if (amount.length() <= 0 || !isDouble(amount) || !isPositive(amount)){
                amountShaker.shake();
            }

            if (incomeDesciptionComboBox.getSelectionModel().isEmpty()) {
                descriptionShaker.shake();
            }
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

    public boolean isPositive(String num){
        double n = Double.parseDouble(num);

        return n > 0;
    }
}
