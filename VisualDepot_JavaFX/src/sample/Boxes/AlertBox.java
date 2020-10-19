package sample.Boxes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {
    public void display(String message){
        Stage stagePopup = new Stage();
        Label label1 = new Label(message);
        Button button1 =new Button("Pencereyi Kapat");
        VBox vBox1= new VBox(25);

        vBox1.getChildren().addAll(label1,button1);
        stagePopup.initModality(Modality.APPLICATION_MODAL);
        vBox1.setAlignment(Pos.CENTER);
        vBox1.setPadding(new Insets(50));
        button1.setOnAction(e->{
            stagePopup.close();
        });

        vBox1.getStylesheets().add("viper.css");
        stagePopup.setScene(new Scene(vBox1));

        stagePopup.setMinWidth(600);
        stagePopup.setMinHeight(300);
        stagePopup.setWidth(300);
        stagePopup.setTitle("Depo Görsel");
        stagePopup.setWidth(600);
        stagePopup.setHeight(300);
        stagePopup.show();
    }
}
