package sample;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    FXMLLoader fxmlLoader = new FXMLLoader();//creating FXMLLoader object


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Loading user data...");
    }
}
