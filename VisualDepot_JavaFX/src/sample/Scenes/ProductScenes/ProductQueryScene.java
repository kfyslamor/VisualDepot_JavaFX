package sample.Scenes.ProductScenes;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.Database.MySQLConnection;

import java.sql.SQLException;

public class ProductQueryScene {
    TableView<Product> tableView1;
    TableColumn<Product,String> nameColumn;
    TableColumn<Product,String> urunMiktariColumn;
    TableColumn<Product,String> SKTTarihiColumn;
    TableColumn<Product,String> isExpiredColumn;

    ObservableList<Product> queryResult;
    public Scene display(Stage primaryStage, Scene returnScene) {
        //Declarations:
        TextField queryItemName = new TextField();

        queryItemName.setPromptText("Ürün Adı Giriniz.");
        Button buttonQuery = new Button("Sorgula");
        TableView<Product> tableView1 = new TableView<>();
        //setonaction
        buttonQuery.setOnAction(e->{
            queryItemName.getText();
            System.out.println("queryItemName = " + queryItemName.getText());
            MySQLConnection mySQLConnection = new MySQLConnection();
            try {
                queryResult =
                        mySQLConnection.getTable("SELECT urunAdi,urunMiktari,SKTTarihi FROM urundepo WHERE urunAdi=\"" + queryItemName.getText() + "\";");
                System.out.println("SELECT urunAdi,urunMiktari,SKTTarihi FROM urundepo WHERE urunAdi=\"" + queryItemName.getText() + "\";");
                //TODO Make the query according to our day's standarts you big yoshi.
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }

            //Urun column
            nameColumn = new TableColumn<>("Urun Adı");
            nameColumn.setMinWidth(200);
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("urunAdi"));
            //miktar column
            urunMiktariColumn = new TableColumn<>("Ürün Miktarı");
            urunMiktariColumn.setMinWidth(200);
            urunMiktariColumn.setCellValueFactory(new PropertyValueFactory<>("urunMiktari"));
            //SKT column
            SKTTarihiColumn = new TableColumn<>("Son Kullanım Tarihi");
            SKTTarihiColumn.setMinWidth(200);
            SKTTarihiColumn.setCellValueFactory(new PropertyValueFactory<>("SKTTarihi"));
            //SKT Sorgu column
            isExpiredColumn = new TableColumn<>("SKT Sorgu");
            isExpiredColumn.setMinWidth(200);
            isExpiredColumn.setCellValueFactory(new PropertyValueFactory<>("isExpired"));
            tableView1.setItems(queryResult);

            /*
            * private String irsaliyeNo;
              private LocalDate SKTTarihi;
              private String depoSorumlusu;
              private LocalDate girisTarihi;
              private String urunAdi;
              private int urunMiktari;
            *
            * */
        });//sorgu butonu


        //GUI Components

        //tableView1.getColumns().addAll(nameColumn,urunMiktariColumn,SKTTarihiColumn,isExpiredColumn);
        VBox vBox1 = new VBox(25);
        vBox1.getChildren().addAll(buttonQuery,queryItemName,tableView1);
        vBox1.setPadding(new Insets(100));
        return new Scene(vBox1);
    }
}
