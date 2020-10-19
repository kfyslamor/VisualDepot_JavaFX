package sample.Boxes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DatePickerBox {
    String answer=null;
    public String display(String message){

        Stage stagePopup = new Stage();
        DatePicker datePicker1 = new DatePicker();
        Label label1 = new Label(message);
        Button button1 =new Button("Tamam");
        VBox vBox1= new VBox(25);

        vBox1.getChildren().addAll(label1,datePicker1,button1);

        stagePopup.initModality(Modality.APPLICATION_MODAL);
        vBox1.setAlignment(Pos.CENTER);
        vBox1.setPadding(new Insets(25));

        vBox1.getStylesheets().add("viper.css");
        stagePopup.setScene(new Scene(vBox1));
        button1.setOnAction(e->{
            answer = datePicker1.getValue().toString();
            stagePopup.close();
        });

        //GUI components:
        stagePopup.setTitle("Depo Görsel");
        stagePopup.setMinWidth(600);
        stagePopup.setMinHeight(600);
        stagePopup.setWidth(400);
        stagePopup.setWidth(600);
        stagePopup.setHeight(300);
        stagePopup.showAndWait();
        if(answer==null){
            stagePopup.close();
            new AlertBox().display("Lütfen bir son kullanma tarihi seçiniz.");
        }
        return answer;
    }
}
