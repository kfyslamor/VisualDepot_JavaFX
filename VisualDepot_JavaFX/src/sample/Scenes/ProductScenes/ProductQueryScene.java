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
import java.time.Period;

public class ProductQueryScene {

    //Declarations TableViews
    TableView<Product> tableView1= new TableView<>();
    TableView<Product> tableView2= new TableView<>();
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
    MySQLConnection mySQLConnection = new MySQLConnection();
    Product queryExit;
    TextField queryItemInput = new TextField();
    TextField queryItemQuantity = new TextField();
    public Scene display(Stage primaryStage, Scene returnScene) {
        //Declarations:

        queryItemInput.setPromptText("Depodaki bütün ürünleri görmek için sorgula butonuna tıklayın.");
        queryItemQuantity.setPromptText("Depodan çekilecek ürünün miktarını giriniz.");
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
        isExpiredColumn2 = new TableColumn<Product, String>("SKT Sorgu");

        //TableView<Product> tableView1
        //setOnAction methods:
        buttonQuery.setOnAction(e->buttonQueryClicked());


        returnButton.setOnAction(e->{
            primaryStage.setScene(returnScene);
        });

        buttonExit.setOnAction(e-> {
            try {
                buttonExitClicked();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        });




//TODO If the expiration date is close, change the row colors according how close they are to the expiration date.
        //GUI Components
        tableView1.getColumns().addAll(urunAdiColumn,urunMiktariColumn,SKTTarihiColumn,isExpiredColumn);
        tableView2.getColumns().addAll(urunAdiColumn2,urunMiktariColumn2,SKTTarihiColumn2,isExpiredColumn2);
        GridPane mainBox = new GridPane();
        tableView1.setMinWidth(300);
        tableView2.setMinWidth(250);
        VBox firstColumn = new VBox(25);
        HBox secondRow = new HBox(20);
        firstColumn.getChildren().addAll(queryItemInput,queryItemQuantity,buttonQuery,buttonExit,returnButton);
        secondRow.getChildren().addAll(tableView1,tableView2);
        GridPane.setConstraints(firstColumn,0,0);
        GridPane.setConstraints(secondRow,1,0);
        mainBox.setHgap(250);
        mainBox.setAlignment(Pos.CENTER);
        firstColumn.setAlignment(Pos.TOP_LEFT);
        secondRow.setAlignment(Pos.CENTER_LEFT);
        mainBox.getChildren().addAll(firstColumn,secondRow);
        //vBox1.setPadding(new Insets(100));
        return new Scene(mainBox);
    }

    private void buttonQueryClicked() {
        final String queryItemName= queryItemInput.getText().toUpperCase();
        final String queryItemAmount = queryItemQuantity.getText();

        try {
            if (queryItemName.equals("") && queryItemAmount.equals("")){
                queryResult =
                        mySQLConnection.getTable("SELECT irsaliyeNo,urunAdi,urunMiktari,girisTarihi,SKTTarihi FROM urundepo ORDER BY SKTTarihi ASC;");
            }// if both of the inputs are empty show all of the products.
            else if (!(queryItemName.equals("")) && queryItemAmount.equals("")){
                queryResult =mySQLConnection.getTable("SELECT irsaliyeNo,urunAdi,urunMiktari,girisTarihi,SKTTarihi FROM urundepo WHERE " +
                        "urunAdi LIKE \"%"+queryItemName+"%\" ORDER BY SKTTarihi ASC;");
            }// IF name section NOT empty AND number section empty
            else if((queryItemName.equals("")) && !queryItemAmount.equals("")){
                queryResult =mySQLConnection.getTable("SELECT irsaliyeNo,urunAdi,urunMiktari,girisTarihi,SKTTarihi FROM urundepo WHERE " +
                        "urunMiktari>"+queryItemAmount+" ORDER BY SKTTarihi ASC;");
            }// IF name section empty AND number section NOT empty
            else{
                queryResult =
                        mySQLConnection.getTable("SELECT irsaliyeNo,urunAdi,urunMiktari,girisTarihi,SKTTarihi FROM urundepo WHERE " +
                                "urunAdi LIKE \"%"+queryItemName+"%\" AND urunMiktari>="+queryItemAmount+" ORDER BY SKTTarihi ASC;");
            }
            tableView1.setItems(queryResult);
            // TODO Make the query according to our day's standards you big yoshi.

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        //tableView1.setItems(queryResult);
        //irsaliyeNoColumn.setCellValueFactory(new PropertyValueFactory<Product,String>("irsaliyeNo"));
        urunAdiColumn.setCellValueFactory(new PropertyValueFactory<Product,String>("urunAdi"));
        urunMiktariColumn.setCellValueFactory(new PropertyValueFactory<Product,Integer>("urunMiktari"));
        SKTTarihiColumn.setCellValueFactory(new PropertyValueFactory<Product,LocalDate>("SKTTarihi"));
        isExpiredColumn.setCellValueFactory(new PropertyValueFactory<Product,String>("isExpired"));
        //cikisTarihiColumn.setCellValueFactory(new PropertyValueFactory<Product,LocalDate>("girisTarihi"));
        SKTTarihiColumn.setMinWidth(125);
        isExpiredColumn.setMinWidth(200);
        tableView1.setMinWidth(500);
    }

    private void buttonExitClicked() throws SQLException {
        {
            //TODO https://stackoverflow.com/questions/19821736/mysql-move-rows-from-one-table-to-another
            //TODO çıkışı yapılacak ürünün miktarını vs. değiştir.
            final String queryItemName= queryItemInput.getText().toUpperCase(); // BPC1124
            int queryItemAmount= Integer.parseInt(queryItemQuantity.getText()); // 1500



            Product productToBeSent = new Product();
            boolean alert = ConfirmBox.display("Depo Görsel",queryItemName +" Adlı üründen "+ queryItemAmount + " adet çıkış yapmak istiyor musunuz?");
            /*
            * for (int i = 0; i < queryResult.size(); i++) {
                Period periodBetweenExpirationAndNow = Period.between(queryResult.get(i).getSKTTarihi(),LocalDate.now().plusDays(1));
                {
                    System.out.println("periodBetweenExpirationAndNow = " + periodBetweenExpirationAndNow);
                }
            }*/
            for (int i = 0; i < queryResult.size(); i++) {

            }
            if(alert){
                productToBeSent = queryResult.get(0);
                if (queryItemAmount > productToBeSent.getUrunMiktari()){
                    System.out.println(1 + ".  " + queryItemAmount + ">" + productToBeSent.getUrunMiktari());
                    mySQLConnection.queryToDB("INSERT INTO cikisdepo (irsaliyeNo,urunAdi,urunMiktari,girisTarihi,SKTTarihi,depoSorumlusu) VALUES " +
                            "(\"" +productToBeSent.getIrsaliyeNo() + "\",\""+productToBeSent.getUrunAdi()+"\","+queryItemAmount+
                            ",\""+LocalDate.now().toString()+"\",\""+productToBeSent.getSKTTarihi()+"\",\""+productToBeSent.getDepoSorumlusu()+"\");");
                    queryItemAmount -= productToBeSent.getUrunMiktari();
                    productToBeSent.setUrunMiktari(0);
                    mySQLConnection.queryToDB("DELETE FROM urundepo WHERE urunAdi=\""+ productToBeSent.getUrunAdi()+"\" AND urunMiktari="+queryItemAmount+" LIMIT 1;");
                    //SELECT irsaliyeNo,urunAdi,urunMiktari,SKTTarihi,girisTarihi FROM urundepo WHERE urunAdi="BPC1143" AND urunMiktari=5000 LIMIT 1;
                }
                else if(queryItemAmount == productToBeSent.getUrunMiktari()){
                    System.out.println(2 + ".  " + queryItemAmount + "==" + productToBeSent.getUrunMiktari());

                    mySQLConnection.queryToDB("INSERT INTO cikisdepo (irsaliyeNo,urunAdi,urunMiktari,girisTarihi,SKTTarihi,depoSorumlusu) VALUES " +
                            "(\"" +productToBeSent.getIrsaliyeNo() + "\",\""+productToBeSent.getUrunAdi()+"\","+queryItemAmount+
                            ",\""+LocalDate.now().toString()+"\",\""+productToBeSent.getSKTTarihi()+"\",\""+productToBeSent.getDepoSorumlusu()+"\");");

                    productToBeSent.setUrunMiktari(0);
                    mySQLConnection.queryToDB("DELETE FROM urundepo WHERE urunAdi=\""+ productToBeSent.getUrunAdi()+"\" AND urunMiktari="+queryItemAmount+" LIMIT 1;");

                } // If the required amount equals to
                else if (queryItemAmount < productToBeSent.getUrunMiktari()){
                    System.out.println(3 + ".  " + queryItemAmount + "<" + productToBeSent.getUrunMiktari());
                    //queryitemamount = çıkışı yapılmak istenen miktar
                    //producttobesent.geturunmiktari() = seçili ürünün miktari
                    // seçili ürünün miktarı, çıkışı yapılmak istenen miktardan küçük OLAMAZ.
                    System.out.println(productToBeSent.toString());
                    mySQLConnection.queryToDB("UPDATE urundepo SET urunMiktari= " +(productToBeSent.getUrunMiktari()-(queryItemAmount))+" WHERE urunAdi= \""+queryItemName+"\" AND urunMiktari="+productToBeSent.getUrunMiktari()+" AND irsaliyeNo=\""+productToBeSent.getIrsaliyeNo()+"\"LIMIT 1;");
                    productToBeSent.setUrunMiktari(productToBeSent.getUrunMiktari() - queryItemAmount);

                    mySQLConnection.queryToDB("INSERT INTO cikisdepo (irsaliyeNo,urunAdi,urunMiktari,girisTarihi,SKTTarihi,depoSorumlusu) VALUES " +
                            "(\"" +productToBeSent.getIrsaliyeNo() + "\",\""+productToBeSent.getUrunAdi()+"\","+queryItemAmount+
                            ",\""+LocalDate.now().toString()+"\",\""+productToBeSent.getSKTTarihi()+"\",\""+productToBeSent.getDepoSorumlusu()+"\");");

                    System.out.println("productToBeSent.getUrunMiktari() = " + productToBeSent.getUrunMiktari());
                    System.out.println("queryItemAmount = " + queryItemAmount);
                    }
                }


                //TODO the problem is there is a data conflict when trying to make exit of multiple attributes with the same irsaliyeNo because the irsaliyeNo is the primary key and unique identifier of the elements
                //TODO inside the cikisdepo table.


                tableView2.setItems(queryResult);
                System.out.println("queryItemInput = " + queryItemName);
                System.out.println("queryItemAmount = " + queryItemAmount);

                urunAdiColumn2.setCellValueFactory(new PropertyValueFactory("urunAdi"));
                urunMiktariColumn2.setCellValueFactory(new PropertyValueFactory("urunMiktari"));
                SKTTarihiColumn2.setCellValueFactory(new PropertyValueFactory("SKTTarihi"));
                isExpiredColumn2.setCellValueFactory(new PropertyValueFactory("isExpired"));
                SKTTarihiColumn2.setMinWidth(125);
            }

             // Add the necessary options for a healthy output.
        }
}
//TODO currently unable to exit the products with the same "irsaliyeNo"