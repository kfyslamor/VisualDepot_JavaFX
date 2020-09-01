package sample.Scenes;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Product {
    // Product's attributes, later they are all going to be columns.
    private String name;
    private double price;
    private int quantity;
    TableView<Product> productTableView;
    //Whenever you create a table or a spreadsheet, you NEED at least 1 column, in this case we will have 3 columns
    //where the columns will represent the attributes of the product.


    public Product(){
        this.name = "";
        this.price = 0;
        this.quantity=0;
    }
    public Product(String name,double price, int quantity){
        this.name = name;
        this.price = price;
        this.quantity=quantity;
    }
    public Scene display(Stage primaryStage, Scene mainScene){
        //Declarations:
        Button but1 = new Button("Return");
        Button but2 = new Button("Submit");

        //Name Column:
        TableColumn<Product,String> nameColumn = new TableColumn<>("Name"); //HEADER
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<Product,String>("name")); //STREAM, MUST BE THE SAME
        //Price Column:
        TableColumn<Product, Double> priceColumn = new TableColumn<Product, Double>("Price");
        priceColumn.setMinWidth(100);
        priceColumn.setCellValueFactory(new PropertyValueFactory<Product,Double>("price"));
        //Quantity Column:
        TableColumn<Product,Integer> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setMinWidth(100);
        quantityColumn.setCellValueFactory(new PropertyValueFactory<Product,Integer>("quantity"));

        productTableView = new TableView<>();
        productTableView.setItems(getProduct());

        productTableView.getColumns().addAll(nameColumn,priceColumn,quantityColumn);



        //setOnAction:
        but1.setOnAction(e->{
            primaryStage.setScene(mainScene);
        });

        but2.setOnAction(e->{

        });
        //GUI Components:
        VBox vBox1 = new VBox(25);
        vBox1.setPadding(new Insets(20));
        vBox1.getChildren().addAll(productTableView,but1,but2);
        return new Scene(vBox1);
    }
    //Get all of the products:
    public ObservableList<Product> getProduct(){
        ObservableList<Product> products = FXCollections.observableArrayList();
        products.add(new Product("laptop",500,20));
        products.add(new Product("toilet",500,31));
        products.add(new Product("DVD",500,2044));
        products.add(new Product("Corn",500,2222));
        products.add(new Product("Beeg Yoshee",999,120));
        return products;
    }
    //This would typically be like connecting to a DATABASE or a FILE.
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
