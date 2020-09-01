package sample.Boxes;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ListBox {

    ListView<String> listView1 = new ListView<>();
    public Scene display(Stage primaryStage, Scene mainScene){
        //Declarations:
        Button but1 = new Button("Submit");
        Button but2 = new Button("Return");

        listView1.getItems().addAll("a","b","c","d");
        listView1.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); // Allows you to choose multiple

        VBox layout = new VBox(20);
        layout.setPadding(new Insets(40));
        layout.getChildren().addAll(listView1,but1,but2);

        //setOnAction:
        but1.setOnAction(e->{
            printList();
        });
        but2.setOnAction(e->{
            primaryStage.setScene(mainScene);
        });
        //GUI Components:
        return new Scene(layout);
    }

    private void printList(){
        StringBuilder message = new StringBuilder();
        ObservableList<String> movies;
        movies = listView1.getSelectionModel().getSelectedItems();
        //getSelectionModel provides the items inside that List.
        for (String m :
                movies) {
            message.append(m).append("\n");
        }
        System.out.println("movies = " + movies);
    }
}
