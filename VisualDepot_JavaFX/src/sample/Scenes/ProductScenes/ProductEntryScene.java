package sample.Scenes.ProductScenes;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.Database.MySQLConnection;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

/**
 * Handling the entry of products.
 */

/*
gridpane.add(new Button(), 1, 0); // column=1 row=0
gridpane.add(new Label(), 2, 0);  // column=2 row=0

* */
public class ProductEntryScene {
    public Scene display(Stage primaryStage, Scene scene1){
        //1st Layout, 1st column
        VBox vBox1 = new VBox(7);
        //irsaliyeNo input
        TextField irsaliyeNoInput = new TextField();
        irsaliyeNoInput.setPromptText("İrsaliye numarası giriniz...");

        vBox1.getChildren().addAll(new Label("İrsaliye numarası"), irsaliyeNoInput);


        //2nd Layout, 2nd column
        VBox vBox2 = new VBox(7);
        //urunadi input
        TextField urunInput = new TextField();
        urunInput.setPromptText("Ürün adı giriniz...");
        vBox2.getChildren().addAll(new Label("Ürün Adı"),urunInput);

        VBox vBox3 = new VBox(7);
        //miktar input
        TextField miktarInput = new TextField();
        miktarInput.setPromptText("Miktar giriniz...");
        vBox3.getChildren().addAll(new Label("Miktar"),miktarInput);

        VBox vBox4 = new VBox(7);
        //giristarihi input
        DatePicker datePicker1 = new DatePicker(LocalDate.now());
        datePicker1.setPromptText("Gün/Ay/Yıl");
        //TextField giristarihiInput = new TextField();
        SimpleDateFormat format = new SimpleDateFormat();
        //giristarihiInput.setPromptText("Giriş tarihi giriniz (Gün-Ay-Yıl)");
        vBox4.getChildren().addAll(new Label("Giriş Tarihi"),datePicker1);
        VBox vBox5 = new VBox(7);
        //depoSorumlusu input
        TextField depoSorumlusuInput = new TextField();
        depoSorumlusuInput.setPromptText("İsim Soyisim");
        vBox5.getChildren().addAll(new Label("Depo Sorumlusu"),depoSorumlusuInput);

        //Buttons
        Button productEntryButton = new Button("Kayıt Yap");
        GridPane.setConstraints(productEntryButton,5,0);
        Button returnButton= new Button("Geri Dön");
        GridPane.setConstraints(returnButton,5,1);
        VBox vBox6 = new VBox(7);
        vBox6.getChildren().addAll(productEntryButton,returnButton);

        //Embedding layouts for UI
        HBox hBoxMain = new HBox(25);
        hBoxMain.setPadding(new Insets(100));


        hBoxMain.getChildren().addAll(vBox1,vBox2,vBox3,vBox4,vBox5,vBox6);




        // column row
        /*

        * */



        //GUI Components:
        //flowPane.getChildren().addAll(irsaliyeNoLabel,irsaliyeNoInput,urunLabel,urunInput);
        //urunLabel,urunInput,miktarLabel,miktarInput,giristarihiLabel,giristarihiInput,depoSorumlusuLabel,depoSorumlusuInput
        //setOnAction:
        productEntryButton.setOnAction(e->{
            //Date
            LocalDate entryDate = datePicker1.getValue();
            System.out.println("entryDate = " + entryDate);

            LocalDate lastUseDate = datePicker1.getValue().plusMonths(1);
            System.out.println("lastUseDate = " + lastUseDate);

            MySQLConnection mySQLConnection1 = new MySQLConnection();
            try {
                mySQLConnection1.insertToTable("VALUES(\" "+ irsaliyeNoInput.getText().toUpperCase() + "\",\"" + urunInput.getText().toUpperCase() +"\","
                        + miktarInput.getText() + ",\"" + entryDate.toString() + "\",\"" + lastUseDate.toString() + "\",\"" + depoSorumlusuInput.getText() + "\");"
                );
                System.out.println("KAYIT BAŞARILI");
                mySQLConnection1.getTable("SELECT * FROM urundepo");
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }

        });// Mal kayıt butonu.
        returnButton.setOnAction(e->{
            primaryStage.setScene(scene1);
        });
        return new Scene(hBoxMain);
    }
}
//TODO https://stackoverflow.com/questions/34329852/how-to-insert-date-values-into-table
//TODO make sure no faulty input gets into the database and throws an error instead.