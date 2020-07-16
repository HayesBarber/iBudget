package sample.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXListCell;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import sample.model.Income;

public class incomeCellController extends JFXListCell<Income> {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label amountLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private ImageView imageView;

    private FXMLLoader fxmlLoader;

    @FXML
    void initialize() {
    }

    @Override
    public void updateItem(Income income, boolean empty){
        super.updateItem(income,empty);

        if (empty || income == null) {
            setText(null);
            setGraphic(null);
        }else {
            if (fxmlLoader == null ) {
                fxmlLoader = new FXMLLoader(getClass()
                        .getResource("/sample/view/incomeCell.fxml"));
                fxmlLoader.setController(this);

                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            amountLabel.setText(String.format("$%.02f",income.getIncomeAmount()));
            dateLabel.setText(income.getIncomeDate());

            String description = income.getIncomeDescription();
            Image image;

            switch (description) {
                case "Cash":
                    image = new Image("/sample/assets/cash.png");
                    imageView.setImage(image);
                    break;
                case "Check":
                    image = new Image("/sample/assets/check.png");
                    imageView.setImage(image);
                    break;
                default:
                    image = new Image("/sample/assets/other.png");
                    imageView.setImage(image);
                    break;
            }


            setText(null);
            setGraphic(rootPane);

        }
    }
}
