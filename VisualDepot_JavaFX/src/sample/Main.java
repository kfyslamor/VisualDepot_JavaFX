package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.Boxes.ConfirmBox;
import sample.Scenes.LoginScene;
import sample.Scenes.ProductScenes.Product;
import sample.Scenes.SignupScene;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Depo Görsel");
        primaryStage.setMinWidth(1366);
        primaryStage.setMinHeight(768);
        primaryStage.setMaxWidth(1366);
        primaryStage.setMaxHeight(768);
        primaryStage.setScene(entryScene(primaryStage));

        primaryStage.setOnCloseRequest(e->{
            e.consume();
            boolean answer = ConfirmBox.display(primaryStage.getTitle(),"Do you want to quit?");
            if (answer){
                Platform.exit();
            }
        });
        primaryStage.show();
    }
    private Scene entryScene(Stage primaryStage){
        //Declarations:
        Scene scene1;
        VBox vBox1 = new VBox(50);
        vBox1.setPadding(new Insets(100));
        vBox1.setAlignment(Pos.CENTER);
        Button but1 = new Button("Giriş");
        Button signupButton = new Button("Kayıt Ol");
        Button productButton = new Button("Ürün Örnek");
        Button productEntryBut = new Button("Mal Giriş");
        Button productStorageQuery = new Button("Mal Sorgula");
        //GUI Components:
        vBox1.getChildren().addAll(but1,signupButton,productButton);
        scene1 = new Scene(vBox1);
        //setOnAction:
        but1.setOnAction(e-> primaryStage.setScene(new LoginScene().display(primaryStage,scene1)));
        signupButton.setOnAction(e-> primaryStage.setScene(new SignupScene().display(primaryStage,scene1)));
        productButton.setOnAction(e->{
            //primaryStage.setScene(new Product().display(primaryStage,scene1));
        });
        productEntryBut.setOnAction(e->{

        });
        productStorageQuery.setOnAction(e->{

        });


        return scene1;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
/*
TODO Learn exception handling
TODO Program shouldn't exit after a faulty register AND/OR a faulty login
 */