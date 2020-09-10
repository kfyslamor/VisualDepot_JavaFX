package sample.Scenes.ProductScenes;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.Database.MySQLConnection;

import java.sql.SQLException;
import java.time.LocalDate;

public class ProductExitScene {
    TableView<Product> tableView1= new TableView<>();
    TableColumn<Product,String> urunAdiColumn;
    TableColumn<Product,Integer> urunMiktariColumn;
    TableColumn<Product, LocalDate> SKTTarihiColumn;
    TableColumn<Product,String> isExpiredColumn;
    TableColumn<Product,String> irsaliyeNoColumn;
    TableColumn<Product,LocalDate> cikisTarihiColumn;
    ObservableList<Product> queryResult;

    MySQLConnection mySQLConnection = new MySQLConnection();

    TextField queryItemInput = new TextField();
    TextField queryItemQuantity = new TextField();
    public Scene display(Stage primaryStage, Scene returnScene){
        //Declarations:

        queryItemInput.setPromptText("Depodaki bütün ürünleri görmek için sorgula butonuna tıklayın.");
        queryItemQuantity.setPromptText("Depodan çekilecek ürünün miktarını giriniz.");

        Button buttonQuery = new Button("Sorgula");
        Button returnButton = new Button("Return");

        urunAdiColumn = new TableColumn<Product, String>("Ürün Adı");
        urunMiktariColumn = new TableColumn<Product, Integer>("Ürün Miktarı");
        cikisTarihiColumn = new TableColumn<>("Depodan Çıkış Tarihi");
        SKTTarihiColumn = new TableColumn<Product, LocalDate>("Son Kullanım Tarihi");
        isExpiredColumn = new TableColumn<Product, String>("SKT Sorgu");
        irsaliyeNoColumn = new TableColumn<Product,String>("İrsaliye No");

        tableView1.getColumns().addAll(irsaliyeNoColumn,urunAdiColumn,urunMiktariColumn,cikisTarihiColumn,SKTTarihiColumn,isExpiredColumn);
        //setOnAction Methods:
        buttonQuery.setOnAction(e->buttonQueryClicked());

        returnButton.setOnAction(e->{
            primaryStage.setScene(returnScene);
        });

        //GUI Components:
        buttonQuery.setMinWidth(100);
        returnButton.setMinWidth(100);

        VBox mainBox = new VBox(10);
        HBox hBoxTop = new HBox();
        HBox hBoxBot = new HBox(10);

        mainBox.setAlignment(Pos.CENTER);
        mainBox.setPadding(new Insets(100));
        hBoxTop.getChildren().add(tableView1);
        hBoxBot.getChildren().addAll(queryItemInput,queryItemQuantity,buttonQuery,returnButton);

        mainBox.getChildren().addAll(hBoxTop, hBoxBot);
        return new Scene(mainBox);
    }

    private void buttonQueryClicked() {
            final String queryItemName= queryItemInput.getText().toUpperCase();
            final String queryItemAmount = queryItemQuantity.getText();

            try {
                if (queryItemName.equals("") && queryItemAmount.equals("")){
                    queryResult =
                            mySQLConnection.getTable("SELECT irsaliyeNo,urunAdi,urunMiktari,girisTarihi,SKTTarihi FROM cikisdepo;");
                }// if both of the inputs are empty show all of the products.
                else if (!(queryItemName.equals("")) && queryItemAmount.equals("")){
                    queryResult =mySQLConnection.getTable("SELECT irsaliyeNo,urunAdi,urunMiktari,girisTarihi,SKTTarihi FROM cikisdepo WHERE " +
                            "urunAdi LIKE \"%"+queryItemName+"%\";");
                }// IF name section NOT empty AND number section empty
                else if((queryItemName.equals("")) && !queryItemAmount.equals("")){
                    queryResult =mySQLConnection.getTable("SELECT irsaliyeNo,urunAdi,urunMiktari,girisTarihi,SKTTarihi FROM cikisdepo WHERE " +
                            "urunMiktari LIKE \"%"+queryItemAmount+"%\";");
                }// IF name section empty AND number section NOT empty
                else{
                    queryResult =
                            mySQLConnection.getTable("SELECT irsaliyeNo,urunAdi,urunMiktari,girisTarihi,SKTTarihi FROM cikisdepo WHERE " +
                                    "urunAdi LIKE \"%"+queryItemName+"%\" AND urunMiktari=\"%"+queryItemAmount+"%\";");
                }
                tableView1.setItems(queryResult);
                // TODO Make the query according to our day's standards you big yoshi.

            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
            //tableView1.setItems(queryResult);
            irsaliyeNoColumn.setCellValueFactory(new PropertyValueFactory<Product,String>("irsaliyeNo"));
            urunAdiColumn.setCellValueFactory(new PropertyValueFactory<Product,String>("urunAdi"));
            urunMiktariColumn.setCellValueFactory(new PropertyValueFactory<Product,Integer>("urunMiktari"));
            SKTTarihiColumn.setCellValueFactory(new PropertyValueFactory<Product,LocalDate>("SKTTarihi"));
            isExpiredColumn.setCellValueFactory(new PropertyValueFactory<Product,String>("isExpired"));
            cikisTarihiColumn.setCellValueFactory(new PropertyValueFactory<Product,LocalDate>("girisTarihi"));
            SKTTarihiColumn.setMinWidth(125);
            isExpiredColumn.setMinWidth(200);
            tableView1.setMinWidth(800);
    }

}
