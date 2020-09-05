package sample.Scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import sample.Database.MySQLConnection;
import sample.Scenes.ProductScenes.ProductEntryScene;
import sample.Scenes.ProductScenes.ProductMenu;
import sample.Security.BCrypt;

import java.sql.SQLException;

public class LoginScene {
    public Scene display(Stage primaryStage, Scene scene1){
        //Declarations:

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(100,100,200,200));
        grid.setVgap(8); // vertical gap.
        grid.setHgap(10);
        //name label
        Label nameLabel = new Label("Username:");
        GridPane.setConstraints(nameLabel,0,0);
        //Name input
        TextField nameInput = new TextField();
        nameInput.setPromptText("Enter name here");
        GridPane.setConstraints(nameInput,1,0);
        //pass label
        Label passLabel = new Label("Password:");
        GridPane.setConstraints(passLabel,0,1);
        //pass input
        PasswordField passField = new PasswordField();
        passField.setPromptText("Enter pass here");
        GridPane.setConstraints(passField,1,1);
        Button loginButton = new Button("Log In");
        GridPane.setConstraints(loginButton,2,1);
        Button returnButton= new Button("Return");
        GridPane.setConstraints(returnButton,2,0);
        //setOnActionMethods:
        loginButton.setOnAction(e->{
            final String name,password;
            name = nameInput.getText();
            password = passField.getText();
            if(checkInput(name,password)){ // check if the input fits into the following rules.
                System.out.println("CORRECT FORM OF INPUT:");
                System.out.println("name = " + name);
                System.out.println("password = " + password);
                System.out.println("<-------*------->");
                System.out.println("Checking Database Standby...");
                MySQLConnection mySQLConnection = new MySQLConnection();
                try {
                    if (BCrypt.checkpw(password,mySQLConnection.checkLogin(name))){
                        System.out.println("--- LOGIN SUCCESSFUL ---");
                        primaryStage.setScene(new ProductMenu().display(primaryStage, scene1));
                    }
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }

            }
            else {
                System.out.println("WRONG FORM OF INPUT:");
                System.out.println("name = " + name);
                System.out.println("password = " + password);
            }

        });
        returnButton.setOnAction(e->{
            primaryStage.setScene(scene1);
        });
        //GUI Components:
        primaryStage.setMinWidth(1366);
        primaryStage.setMinHeight(768);
        primaryStage.setMaxWidth(1366);
        primaryStage.setMaxHeight(768);
        primaryStage.setHeight(768);
        primaryStage.setWidth(1366);
        grid.setAlignment(Pos.CENTER);
        grid.getChildren().addAll(nameLabel,nameInput,passLabel,passField,loginButton,returnButton);
        return new Scene(grid);
    }
    private boolean checkInput(String name,String password){
        boolean answer = false;
        if ((name.length()>= 3) && (name.length() <= 16) && (password.length() >= 3) && (password.length() <= 16) && !(name.equals(password) && name.matches("^[a-zA-Z0-9]+$"))){
            answer = true;
        }

        return answer;
    }
}
//TODO learn to handle logins etc.
