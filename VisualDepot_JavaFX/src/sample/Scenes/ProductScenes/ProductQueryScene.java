package sample.Scenes.ProductScenes;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.Boxes.ConfirmBox;
import sample.Database.MySQLConnection;

import java.sql.SQLException;
import java.time.LocalDate;

public class ProductQueryScene {

    //Declarations TableViews
    TableView<Product> tableView1= new TableView<>();
    TableView<Product> tableView2= new TableView<>();
    TableView<Product> tableView3= new TableView<>();
    //Declarations TableColumns

    TableColumn<Product,String> urunAdiColumn;
    TableColumn<Product,Integer> urunMiktariColumn;
    TableColumn<Product,LocalDate> SKTTarihiColumn;
    TableColumn<Product,String> isExpiredColumn; //TODO <--

    TableColumn<Product,String> urunAdiColumn2;
    TableColumn<Product,Integer> urunMiktariColumn2;
    TableColumn<Product,LocalDate> SKTTarihiColumn2;
    TableColumn<Product,String> isExpiredColumn2; //TODO <--

    ObservableList<Product> queryResult;
    Product queryExit;
    public Scene display(Stage primaryStage, Scene returnScene) {
        //Declarations:
        TextField queryItemInput = new TextField();
        queryItemInput.setPromptText("Depodaki bütün ürünleri görmek için sorgula butonuna tıklayın.");
        //Buttons   
        Button buttonQuery = new Button("Sorgula");
        Button buttonExit = new Button("Kullan / Revize et");
        Button returnButton = new Button("Return");
        buttonQuery.setMinWidth(100);
        buttonExit.setMinWidth(100);
        returnButton.setMinWidth(100);
        //Boxes
        VBox placeHolderVbox = new VBox(5);
        VBox placeHolderVbox2 = new VBox(5);
        placeHolderVbox.setAlignment(Pos.CENTER);
        placeHolderVbox2.setAlignment(Pos.CENTER);
        placeHolderVbox.getChildren().add(new Label("Sorgula butonuna tıkladıktan sonra görmek istediğiniz ürünleri burada görebilirsiniz."));
        placeHolderVbox2.getChildren().add(new Label("Çıkışını yapmak istediğiniz ya da revize etmek istediğiniz ürünleri burada görebilirsiniz."));
        tableView2.setPlaceholder(placeHolderVbox2);
        tableView1.setPlaceholder(placeHolderVbox);
        tableView1.setMinWidth(200);
        tableView2.setMinWidth(200);

        MySQLConnection mySQLConnection = new MySQLConnection();


        //urunAdi column
        urunAdiColumn = new TableColumn<Product, String>("Ürün Adı");
        urunAdiColumn2 = new TableColumn<Product, String>("Ürün Adı");
        //urunMiktari column
        urunMiktariColumn = new TableColumn<Product, Integer>("Ürün Miktarı");
        urunMiktariColumn2 = new TableColumn<Product, Integer>("Ürün Miktarı");
        //SKTTarihi column
        SKTTarihiColumn = new TableColumn<Product, LocalDate>("Son Kullanım Tarihi");
        SKTTarihiColumn2 = new TableColumn<Product, LocalDate>("Son Kullanım Tarihi");
        //isExpired column
        isExpiredColumn = new TableColumn<Product, String>("SKT Sorgu");
        isExpiredColumn2 = new TableColumn<>("SKT Sorgu");

        //TableView<Product> tableView1
        //setOnAction methods:
        buttonQuery.setOnAction(e->{
            try {
                if (queryItemInput.getText().equals("")){
                    queryResult =
                            mySQLConnection.getTable("SELECT * FROM urundepo;");
                    for (int i = 0; i < queryResult.size(); i++) {
                        queryResult.get(i).setIsExpired();
                    }
                }
                else{
                    queryResult =
                            mySQLConnection.getTable("SELECT urunAdi,urunMiktari,SKTTarihi FROM urundepo WHERE urunAdi=\"" + queryItemInput.getText().toUpperCase() + "\";");
                    for (int i = 0; i < queryResult.size(); i++) {
                        queryResult.get(i).setIsExpired();
                    }
                }

                //System.out.println("SELECT urunAdi,urunMiktari,SKTTarihi FROM urundepo WHERE urunAdi=\"" + queryItemInput.getText() + "\";");
                tableView1.setItems(queryResult);

                // TODO Make the query according to our day's standards you big yoshi.

            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
            urunAdiColumn.setCellValueFactory(new PropertyValueFactory("urunAdi"));
            urunMiktariColumn.setCellValueFactory(new PropertyValueFactory("urunMiktari"));
            SKTTarihiColumn.setCellValueFactory(new PropertyValueFactory("SKTTarihi"));
            isExpiredColumn.setCellValueFactory(new PropertyValueFactory("isExpired"));
            //TODO https://stackoverflow.com/questions/55874952/javafx-propertyvaluefactory-is-not-able-to-retrieve-property
            SKTTarihiColumn.setMinWidth(125);
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



        returnButton.setOnAction(e->{
            primaryStage.setScene(returnScene);
        });

        buttonExit.setOnAction(e->{
            final String queryItemName= queryItemInput.getText().toUpperCase();
            System.out.println("queryItemInput = " + queryItemName);
            try{
                queryResult = mySQLConnection.getTable("SELECT irsaliyeNo,urunAdi,urunMiktari,SKTTarihi FROM urundepo " +
                        "WHERE urunAdi=\"" + queryItemName + "\";");
                for (int i = 0; i < queryResult.size(); i++) {
                    queryResult.get(i).setIsExpired();
                }
                tableView2.setItems(queryResult);
                boolean alert = ConfirmBox.display(primaryStage.getTitle(),queryItemName +" Adlı ürünün çıkışını yapmak istiyor musunuz?");
                if(alert){
                    mySQLConnection.queryToDBDelete("urundepo","urunAdi",queryItemName);
                }
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }


            finally{
                urunAdiColumn2.setCellValueFactory(new PropertyValueFactory("urunAdi"));
                urunMiktariColumn2.setCellValueFactory(new PropertyValueFactory("urunMiktari"));
                SKTTarihiColumn2.setCellValueFactory(new PropertyValueFactory("SKTTarihi"));
                isExpiredColumn2.setCellValueFactory(new PropertyValueFactory("isExpired"));
                SKTTarihiColumn2.setMinWidth(125);
            } // Add the necessary options for a healthy output.
        });





        //GUI Components
        tableView1.getColumns().addAll(urunAdiColumn,urunMiktariColumn,SKTTarihiColumn,isExpiredColumn);
        tableView2.getColumns().addAll(urunAdiColumn2,urunMiktariColumn2,SKTTarihiColumn2,isExpiredColumn2);
        GridPane mainBox = new GridPane();

        HBox firstRow = new HBox(20);
        HBox secondRow = new HBox(20);
        firstRow.getChildren().addAll(queryItemInput,buttonQuery,buttonExit,returnButton);
        secondRow.getChildren().addAll(tableView1,tableView2);
        GridPane.setConstraints(firstRow,0,0);
        GridPane.setConstraints(secondRow,1,0);
        mainBox.setHgap(250);
        mainBox.setAlignment(Pos.CENTER);
        firstRow.setAlignment(Pos.TOP_LEFT);
        secondRow.setAlignment(Pos.CENTER_LEFT);
        mainBox.getChildren().addAll(firstRow,secondRow);
        //vBox1.setPadding(new Insets(100));
        return new Scene(mainBox);
    }
}
