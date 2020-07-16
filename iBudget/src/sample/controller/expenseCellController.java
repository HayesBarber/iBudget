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
import sample.model.Expense;

public class expenseCellController extends JFXListCell<Expense> {

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
    public void updateItem(Expense expense, boolean empty){

        super.updateItem(expense,empty);

        if (empty || expense == null) {
            setText(null);
            setGraphic(null);
        }else {
            if (fxmlLoader == null ) {
                fxmlLoader = new FXMLLoader(getClass()
                        .getResource("/sample/view/expenseCell.fxml"));
                fxmlLoader.setController(this);

                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            amountLabel.setText(String.format("$%.02f",expense.getExpenseAmount()));
            dateLabel.setText(expense.getExpenseDate());

            String description = expense.getExpenseDescription();
            Image image;

            switch (description) {
                case "Housing":
                    image = new Image("/sample/assets/home.png");
                    imageView.setImage(image);
                    break;
                case "Fun":
                    image = new Image("/sample/assets/fun.png");
                    imageView.setImage(image);
                    break;
                case "Utilities":
                    image = new Image("/sample/assets/utilites.png");
                    imageView.setImage(image);
                    break;
                case "Groceries":
                    image = new Image("/sample/assets/groceries.png");
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
