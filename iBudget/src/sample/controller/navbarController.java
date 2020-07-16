package sample.controller;

import com.jfoenix.controls.JFXButton;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

import com.jfoenix.controls.JFXTextField;
import com.sun.xml.internal.ws.api.ha.StickyFeature;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import sample.Database.DatabaseHandler;
import sample.animations.Shaker;

public class navbarController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXButton homeButton;

    @FXML
    private JFXButton walletButton;

    @FXML
    private JFXButton spendingButton;

    @FXML
    private JFXButton logoutButton;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Polygon accountPolygon;

    @FXML
    private AnchorPane accountPane;

    @FXML
    private JFXButton accountButton;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private JFXButton editAccountButton;

    @FXML
    private JFXButton deleteAccountButton;

    @FXML
    private AnchorPane editAccountPane;

    @FXML
    private JFXButton saveButton;

    @FXML
    private JFXButton cancelButton;

    @FXML
    private JFXTextField newUsernameTextfield;

    @FXML
    private JFXTextField newPasswordTextfield;

    @FXML
    private AnchorPane deleteAccountPane;

    @FXML
    private JFXButton yesDeleteButton;

    @FXML
    private JFXButton noDeleteButton;


    private DatabaseHandler databaseHandler = new DatabaseHandler();

    public static int userid;

    @FXML
    void initialize() {

        setLabels();

        try {
            AnchorPane Pane = FXMLLoader.load(getClass().getResource("/sample/view/home.fxml"));

            rootPane.getChildren().setAll(Pane);
        } catch (IOException e) {
            e.printStackTrace();
        }

        deleteAccountButton.setOnAction(event -> {
            deleteAccountPane.setVisible(true);
        });

        noDeleteButton.setOnAction(event -> {
            deleteAccountPane.setVisible(false);
        });

        yesDeleteButton.setOnAction(event -> {
            deleteAccount();
        });

        editAccountButton.setOnAction(event -> {

            editAccountPane.setVisible(true);

        });

        saveButton.setOnAction(event -> {

            updateAccount();

        });

        cancelButton.setOnAction(event -> {
            editAccountPane.setVisible(false);
        });

        homeButton.setOnAction(event -> {
            try {
                AnchorPane Pane = FXMLLoader.load(getClass().getResource("/sample/view/home.fxml"));

                rootPane.getChildren().setAll(Pane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        walletButton.setOnAction(event -> {
            try {
                AnchorPane Pane = FXMLLoader.load(getClass().getResource("/sample/view/wallet.fxml"));

                rootPane.getChildren().setAll(Pane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        spendingButton.setOnAction(event -> {
            try {
                AnchorPane Pane = FXMLLoader.load(getClass().getResource("/sample/view/spending.fxml"));

                rootPane.getChildren().setAll(Pane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        accountButton.setOnAction(event -> {
            if (accountPane.isVisible()){
                accountPane.setVisible(false);
                accountPolygon.setVisible(false);
            }else {
                accountPane.setVisible(true);
                accountPolygon.setVisible(true);
            }

            if (editAccountPane.isVisible()){
                editAccountPane.setVisible(false);
            }

            if (deleteAccountPane.isVisible()){
                deleteAccountPane.setVisible(false);
            }

        });

        logoutButton.setOnAction(event -> {

            logoutButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/view/login.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        });

    }

    private void deleteAccount() {

        databaseHandler.deleteUser(userid);

        logoutButton.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/view/login.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

    }

    private void setLabels() {

        usernameLabel.setText("Username: "+databaseHandler.getUsername(userid));
        passwordLabel.setText("Password: "+databaseHandler.getPassword(userid));

    }

    private void updateAccount() {

        String username = newUsernameTextfield.getText().trim();
        String password = newPasswordTextfield.getText().trim();
        boolean exists = databaseHandler.doesUserNameExist(username);

        if (username.length() > 0 && password.length() > 0 && !exists){
            databaseHandler.updateUsername(userid, username);
            databaseHandler.updatePassword(userid, password);
            newPasswordTextfield.setText("");
            newUsernameTextfield.setText("");
            setLabels();
            editAccountPane.setVisible(false);
        } else if (username.length() > 0 && !exists){
            databaseHandler.updateUsername(userid, username);
            newUsernameTextfield.setText("");
            setLabels();
            editAccountPane.setVisible(false);
        }else if (password.length() > 0){
            databaseHandler.updatePassword(userid, password);
            newPasswordTextfield.setText("");
            setLabels();
            editAccountPane.setVisible(false);
        }else if (exists){
            Shaker shaker = new Shaker(newUsernameTextfield);
            shaker.shake();
        }

    }
}
