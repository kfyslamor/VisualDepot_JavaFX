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
import sample.Boxes.AlertBox;
import sample.Boxes.ConfirmBox;
import sample.Boxes.DatePickerBox;
import sample.Database.MySQLConnection;

import java.sql.SQLException;
import java.time.LocalDate;

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
        Button buttonExit = new Button("Kullan");
        Button buttonRevise = new Button("Revize et");
        Button returnButton = new Button("Geri dön");
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
        buttonQuery.setOnAction(e->{
            try{
                buttonQueryClicked();
            }
            catch (Exception e1){
                new AlertBox().display(e1.getLocalizedMessage());
            }
        });
        buttonRevise.setOnAction(e->{
            try{
                buttonReviseClicked();
            }
            catch (Exception e1){
                new AlertBox().display(e1.getLocalizedMessage());
            }
        });


        returnButton.setOnAction(e->{
            primaryStage.setScene(returnScene);
        });

        buttonExit.setOnAction(e-> {
            try {
                buttonExitClicked();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();

            }
            catch (Exception e1){
                if (e1.getMessage().equals("For input string: \"\"")){
                    new AlertBox().display("Mal çıkışı yapabilmek için ürün adını ve miktarını doğru yazdığınızdan emin olunuz.");
                }

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
        firstColumn.getChildren().addAll(queryItemInput,queryItemQuantity,buttonQuery,buttonExit,buttonRevise,returnButton);
        secondRow.getChildren().addAll(tableView1,tableView2);
        GridPane.setConstraints(firstColumn,0,0);
        GridPane.setConstraints(secondRow,1,0);
        mainBox.setHgap(250);
        mainBox.setAlignment(Pos.CENTER);
        firstColumn.setAlignment(Pos.TOP_LEFT);
        secondRow.setAlignment(Pos.CENTER_LEFT);
        mainBox.getChildren().addAll(firstColumn,secondRow);
        //vBox1.setPadding(new Insets(100));
        mainBox.getStylesheets().add("viper.css");
        return new Scene(mainBox);
    }

    private void buttonReviseClicked() throws Exception{
        final String queryItemName= queryItemInput.getText().toUpperCase();
        final String queryItemAmount = queryItemQuantity.getText();
        int totalToBeRevised = 0;
        Product productToBeSent;
        boolean alert = false;
        if (!queryItemName.equals("") && !queryItemAmount.equals("")){
            alert = ConfirmBox.display(queryItemName +" adlı üründen "+ queryItemAmount + " adet revize etmek istiyor musunuz?");
            /*queryResult =
                    mySQLConnection.getTable("SELECT irsaliyeNo,urunAdi,urunMiktari,girisTarihi,SKTTarihi FROM urundepo WHERE " +
                            "urunAdi=\""+queryItemName+"\" AND urunMiktari="+queryItemAmount+" ORDER BY SKTTarihi ASC LIMIT 1;");*/
        }
        else{
            new AlertBox().display("Lütfen girdiğiniz değerleri kontrol ediniz.");
        }

        if (alert){
            /*            for (int i = 0; i < queryResult.size(); i++) {
                productToBeSent = queryResult.get(i);
                if (productToBeSent.getIsExpired().equals("SON KULLANIM TARİHİ GEÇMİŞ")){
                    totalToBeRevised += productToBeSent.getUrunMiktari();
                }
            }
            */
            //alert2 = ConfirmBox.display(queryItemName + " adlı üründen "+queryItemAmount+" adet revize etmek istiyor musunuz?");
            String newExpirationDate=null;

            newExpirationDate = new DatePickerBox().display("Yeni son kullanma tarihini giriniz.");

            if (newExpirationDate!=null){
                mySQLConnection.queryToDB("UPDATE urundepo SET SKTTarihi=\""+ newExpirationDate +"\" WHERE urunAdi=\""+queryItemName+"\" AND urunMiktari="+queryItemAmount+" LIMIT 1;");
            }
            else{
                System.out.println("ERROR");
            }
        }
    }

    //TODO Make sure to revise the products that has expired.
    private void buttonQueryClicked() {
        final String queryItemName= queryItemInput.getText().toUpperCase();
        final String queryItemAmount = queryItemQuantity.getText();

        if (queryItemName.equals("") && queryItemAmount.equals("")){
            queryResult =
                    mySQLConnection.getTable("SELECT irsaliyeNo,urunAdi,urunMiktari,girisTarihi,SKTTarihi FROM urundepo ORDER BY SKTTarihi ASC;");
        }// if both of the inputs are empty show all of the products.
        else if (!(queryItemName.equals("")) && queryItemAmount.equals("")){
            queryResult =mySQLConnection.getTable("SELECT irsaliyeNo,urunAdi,urunMiktari,girisTarihi,SKTTarihi FROM urundepo WHERE " +
                    "urunAdi= \""+queryItemName+"\" ORDER BY SKTTarihi ASC;");
        }// IF name section NOT empty AND number section empty
        else if((queryItemName.equals("")) && !queryItemAmount.equals("")){
            queryResult =mySQLConnection.getTable("SELECT irsaliyeNo,urunAdi,urunMiktari,girisTarihi,SKTTarihi FROM urundepo WHERE " +
                    "urunMiktari>"+queryItemAmount+" ORDER BY SKTTarihi ASC;");
        }// IF name section empty AND number section NOT empty
        else{
            queryResult =
                    mySQLConnection.getTable("SELECT irsaliyeNo,urunAdi,urunMiktari,girisTarihi,SKTTarihi FROM urundepo WHERE " +
                            "urunAdi= \""+queryItemName+"\" AND urunMiktari>="+queryItemAmount+" ORDER BY SKTTarihi ASC;");
        }
        tableView1.setItems(queryResult);
        // TODO Make the query according to our day's standards you big yoshi.

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
            final String queryItemName= queryItemInput.getText().toUpperCase();
            int queryItemAmount= Integer.parseInt(queryItemQuantity.getText());
            final int queryItemAmountOriginal = Integer.parseInt(queryItemQuantity.getText());
            int totalAvailableAmount =0;
            int totalToBeRevised = 0;
            queryResult =
                    mySQLConnection.getTable("SELECT irsaliyeNo,urunAdi,urunMiktari,girisTarihi,SKTTarihi FROM urundepo WHERE " +
                            "urunAdi=\""+queryItemName+"\" ORDER BY SKTTarihi ASC;");

            Product productToBeSent;
            boolean alert = false;
            alert = ConfirmBox.display(queryItemName +" Adlı üründen "+ queryItemAmount + " adet çıkış yapmak istiyor musunuz?");

            /*
            * queryItemAmount = required amount by the end-user
            * productToBeSent.getUrunMiktari() = current product's urunMiktari.
            * */
            if (alert){
                for (int i = 0; i < queryResult.size(); i++) {
                    productToBeSent = queryResult.get(i);
                    if (productToBeSent.getIsExpired().equals("Son Kullanım Tarihi Geçmemiş")){
                        totalAvailableAmount += productToBeSent.getUrunMiktari();
                    }
                }
                int count = 0;
                productToBeSent = queryResult.get(count);
                System.out.println("totalAvailableAmount = " + totalAvailableAmount);
                System.out.println("totalToBeRevised = " + totalToBeRevised);
                if (totalAvailableAmount >= queryItemAmountOriginal){
                    while (queryItemAmount > 0 && count < queryResult.size()){
                        //4020 > 4000
                        if (queryItemAmount > productToBeSent.getUrunMiktari()){
                            System.out.println(2 + ".  " + queryItemAmount + ">" + productToBeSent.getUrunMiktari());

                            mySQLConnection.queryToDB("DELETE FROM urundepo WHERE urunAdi=\""+ productToBeSent.getUrunAdi()+"\" AND urunMiktari="+productToBeSent.getUrunMiktari()+" LIMIT 1;");
                            queryItemAmount -= productToBeSent.getUrunMiktari();
                            System.out.println("queryItemAmount = " + queryItemAmount);
                            totalAvailableAmount += productToBeSent.getUrunMiktari();
                            productToBeSent.setUrunMiktari(0);
                            productToBeSent = queryResult.get(++count);
                        }
                        else if(queryItemAmount == productToBeSent.getUrunMiktari()){
                            System.out.println(3 + ".  " + queryItemAmount + "==" + productToBeSent.getUrunMiktari());

                            mySQLConnection.queryToDB("DELETE FROM urundepo WHERE urunAdi=\""+ productToBeSent.getUrunAdi()+"\" AND urunMiktari="+queryItemAmount+" LIMIT 1;");
                            totalAvailableAmount += productToBeSent.getUrunMiktari();
                            productToBeSent.setUrunMiktari(0);
                            queryItemAmount=0;
                        }
                        else if (queryItemAmount < productToBeSent.getUrunMiktari()){
                            System.out.println(1 + ".  " + queryItemAmount + "<" + productToBeSent.getUrunMiktari());
                            //queryitemamount = çıkışı yapılmak istenen miktar
                            //producttobesent.geturunmiktari() = seçili ürünün miktari
                            // seçili ürünün miktarı, çıkışı yapılmak istenen miktardan küçük OLAMAZ.
                            System.out.println(productToBeSent.toString());
                            mySQLConnection.queryToDB("UPDATE urundepo SET urunMiktari= " +(productToBeSent.getUrunMiktari()-(queryItemAmount))+" WHERE urunAdi= \""+queryItemName+"\" AND urunMiktari="+productToBeSent.getUrunMiktari()+" AND irsaliyeNo=\""+productToBeSent.getIrsaliyeNo()+"\"LIMIT 1;");
                            productToBeSent.setUrunMiktari(productToBeSent.getUrunMiktari() - queryItemAmount);
                            queryItemAmount=0;

                            System.out.println("productToBeSent.getUrunMiktari() = " + productToBeSent.getUrunMiktari());
                            System.out.println("queryItemAmount = " + queryItemAmount);
                        }
                    }
                    mySQLConnection.queryToDB("INSERT INTO cikisdepo (irsaliyeNo,urunAdi,urunMiktari,girisTarihi,SKTTarihi,depoSorumlusu) VALUES " +
                            "(\"" +productToBeSent.getIrsaliyeNo() + "\",\""+productToBeSent.getUrunAdi()+"\","+queryItemAmountOriginal+
                            ",\""+LocalDate.now().toString()+"\",\""+productToBeSent.getSKTTarihi()+"\",\""+productToBeSent.getDepoSorumlusu()+"\");");

                }
                else{
                    new AlertBox().display("Deponuzda "+productToBeSent.getUrunAdi()+" ürününden "+queryItemAmount+" adet bulunmamaktadır.\nDeponuzda "+productToBeSent.getUrunAdi()+" ürününden toplam "
                    +totalAvailableAmount+ " adet bulunmaktadır.\nRevize edilmesi gereken miktar: "+totalToBeRevised+" adettir.");
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