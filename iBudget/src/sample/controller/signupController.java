package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import sample.Database.DatabaseHandler;
import sample.animations.Shaker;
import sample.model.User;

public class signupController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private JFXButton signupButton;

    @FXML
    private JFXTextField usernameTextfield;

    @FXML
    private JFXTextField passwordTextfield;

    @FXML
    private JFXTextField salaryTextfield;

    @FXML
    private JFXButton loginButton;

    private DatabaseHandler databaseHandler;

    @FXML
    void initialize() {

        loginButton.setOnAction(event -> {

            try {
                AnchorPane Pane = FXMLLoader.load(getClass().getResource("/sample/view/login.fxml"));

                rootPane.getChildren().setAll(Pane);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        signupButton.setOnAction(event -> {
            createUser();
        });

    }

    private void createUser() {
        databaseHandler = new DatabaseHandler();

        String username = usernameTextfield.getText().trim();
        String password = passwordTextfield.getText().trim();
        String salary = salaryTextfield.getText().trim();
        boolean exists = databaseHandler.doesUserNameExist(username);


        if (isDouble(salary) && username.length() > 0 && password.length() > 0 && salary.length() > 0 && !exists){
            double salaryNum = Double.parseDouble(salary);
            User user = new User(username,password,salaryNum);

            databaseHandler.signUpUser(user);

            try {
                AnchorPane Pane = FXMLLoader.load(getClass().getResource("/sample/view/login.fxml"));

                rootPane.getChildren().setAll(Pane);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            Shaker usernameShaker = new Shaker(usernameTextfield);
            Shaker passwordShaker = new Shaker(passwordTextfield);
            Shaker salayShaker = new Shaker(salaryTextfield);

            if (username.length() <= 0){
                usernameShaker.shake();
            }

            if (password.length() <= 0){
                passwordShaker.shake();
            }

            if (salary.length() <= 0) {
                salayShaker.shake();
            }

            if (!isDouble(salary)){
                salayShaker.shake();
            }

            if (exists){
                usernameShaker.shake();
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

}

