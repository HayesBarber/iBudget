package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.Database.DatabaseHandler;
import sample.animations.Shaker;
import sample.model.User;

public class loginController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private JFXTextField usernameTextfield;

    @FXML
    private JFXPasswordField passwordTextfield;

    @FXML
    private JFXButton loginButton;

    @FXML
    private JFXButton signupButton;

    private DatabaseHandler databaseHandler;

    @FXML
    void initialize() {

        databaseHandler = new DatabaseHandler();

        signupButton.setOnAction(event -> {
            try {
                AnchorPane Pane = FXMLLoader.load(getClass().getResource("/sample/view/signup.fxml"));

                rootPane.getChildren().setAll(Pane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        loginButton.setOnAction(event -> {

            loginUser();

        });

        passwordTextfield.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER))
            {
                loginUser();
            }
        });

        usernameTextfield.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER))
            {
                loginUser();
            }
        });


    }

    private void loginUser() {

        String username = usernameTextfield.getText().trim();
        String password = passwordTextfield.getText().trim();

        if (username.length() > 0 && password.length() > 0){
            User user = new User();

            user.setUsername(username);
            user.setPassword(password);

            ResultSet rs = databaseHandler.loginUser(user);

            int flag = 0;

            try {
                while (rs.next()){
                    flag++;

                    navbarController.userid = rs.getInt("userid");

                }

                if (flag == 1){
                    login();
                }else {

                    Shaker emailShaker = new Shaker(usernameTextfield);
                    Shaker passwordShaker = new Shaker(passwordTextfield);
                    passwordShaker.shake();
                    emailShaker.shake();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }

        }else {
            Shaker emailShaker = new Shaker(usernameTextfield);
            Shaker passwordShaker = new Shaker(passwordTextfield);
            passwordShaker.shake();
            emailShaker.shake();
        }


    }

    private void login(){

        loginButton.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("/sample/view/navbar.fxml"));

        try {
            loader.setRoot(loader.getRoot());
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));

        stage.show();


    }
}
