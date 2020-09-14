package sample.Boxes;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import static javafx.stage.Modality.APPLICATION_MODAL;

public class ConfirmBox {
    static boolean answer;
    public static boolean display(String title,String question){
        //Declarations:
        int count=0;
        Stage stagePopup = new Stage();
        stagePopup.setTitle(title);
        Button yesB = new Button("Evet");
        Button noB = new Button("Hayır");
        Label labelQuestion = new Label(question);
        
        //setOnActionMethod:
        yesB.setOnAction(e -> {
            answer = true;
            stagePopup.close();
        });
        noB.setOnAction(e -> {
            answer = false;
            stagePopup.close();
        });



        //GUI Components:
        HBox hbox1 = new HBox(100);
        hbox1.setPadding(new Insets(50));
        hbox1.getChildren().addAll(labelQuestion,yesB,noB);
        Scene scene1 = new Scene(hbox1);
        scene1.getStylesheets().add("viper.css");
        stagePopup.setScene(scene1);
        stagePopup.setMinWidth(200);
        stagePopup.setMinHeight(150);
        stagePopup.initModality(APPLICATION_MODAL);
        stagePopup.showAndWait();
        return answer;
    }
}
