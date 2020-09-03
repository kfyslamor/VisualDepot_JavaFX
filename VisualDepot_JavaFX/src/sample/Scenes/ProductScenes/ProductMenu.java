package sample.Scenes.ProductScenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ProductMenu {
    Scene productMenu;
    public Scene display(Stage primaryStage, Scene returnScene){
        //Declarations:
        Button productEntry = new Button("Mal Kayıt");
        Button productQuery = new Button("Mal Sorgu");
        Button productExit = new Button("Mal Çıkış");
        VBox vBox1 = new VBox(50);
        vBox1.setPadding(new Insets(100));
        vBox1.setAlignment(Pos.CENTER);
        //setOnAction:
        productEntry.setOnAction(e->{
            primaryStage.setScene(new ProductEntryScene().display(primaryStage, productMenu));
        });
        productQuery.setOnAction(e->{
            primaryStage.setScene(new ProductQueryScene().display(primaryStage,productMenu));
        });
        productExit.setOnAction(e->{

        });
        //GUI Components:
        vBox1.getChildren().addAll(productEntry,productQuery,productExit);
        productMenu = new Scene(vBox1);
        return productMenu;
    }
}
