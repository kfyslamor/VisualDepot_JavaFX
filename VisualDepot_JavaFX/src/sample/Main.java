package sample;

import com.sun.javafx.application.PlatformImpl;
import com.sun.javafx.css.StyleManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.Boxes.AlertBox;
import sample.Boxes.ConfirmBox;
import sample.Scenes.LoginScene;
import sample.Scenes.ProductScenes.Product;
import sample.Scenes.SignupScene;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        //root.getStylesheets().add(getClass().getResource("/resources/CSS/viper.css").toString());
        primaryStage.setTitle("Depo Görsel");
        primaryStage.setMinWidth(1366);
        primaryStage.setMinHeight(768);
        primaryStage.setMaxWidth(1366);
        primaryStage.setMaxHeight(768);
        Scene mainScene = entryScene(primaryStage);
        //Application.setUserAgentStylesheet(STYLESHEET_MODENA);
        mainScene.getStylesheets().add("viper.css");
        primaryStage.setScene(mainScene);
        Application.setUserAgentStylesheet(Application.STYLESHEET_MODENA);
        //StyleManager.getInstance().addUserAgentStylesheet("viper.css");


        //StyleManager.getInstance().addUserAgentStylesheet(getClass().getResource("/style.css").toString());
        primaryStage.setOnCloseRequest(e->{
            e.consume();
            boolean answer = ConfirmBox.display(primaryStage.getTitle(),"Uygulamadan çıkış yapmak istediğinize emin misiniz?");
            if (answer){
                Platform.exit();
            }
        });
        primaryStage.show();
    }
    private Scene entryScene(Stage primaryStage){
        //Declarations:
        Scene scene1;
        VBox vBox1 = new VBox(50);
        vBox1.setPadding(new Insets(10));
        vBox1.setAlignment(Pos.CENTER);
        Button but1 = new Button("Giriş");
        Button signupButton = new Button("Kayıt Ol");
        //GUI Components:
        setUserAgentStylesheet(STYLESHEET_MODENA);
        vBox1.getChildren().addAll(but1,signupButton);
        scene1 = new Scene(vBox1);

        //setOnAction:
        but1.setOnAction(e-> primaryStage.setScene(new LoginScene().display(primaryStage,scene1)));
        signupButton.setOnAction(e-> primaryStage.setScene(new SignupScene().display(primaryStage,scene1)));


        return scene1;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
/*
TODO Learn exception handling
TODO Program shouldn't exit after a faulty register AND/OR a faulty login and instead should throw an AlertBox.
TODO https://docs.oracle.com/javafx/2/css_tutorial/jfxpub-css_tutorial.htm
 */

/*
*
* Github = https://github.com/kfyslamor/
* Discord= 21kfy#8274 & https://discord.gg/a9WrGS
*
 .d8888b.   d888            d8b                   d8b                            888      d8b      888 d8b 888
d88P  Y88b d8888            Y8P                   Y8P                            888      Y8P      888 Y8P 888
       888   888                                                                 888               888     888
     .d88P   888   .d8888b  888 88888b.  .d8888b  888 88888b.  .d8888b  888  888 88888b.  888  .d88888 888 88888b.   8888b.   .d88b.
 .od888P"    888   88K      888 888 "88b 88K      888 888 "88b 88K      888  888 888 "88b 888 d88" 888 888 888 "88b     "88b d88""88b
d88P"        888   "Y8888b. 888 888  888 "Y8888b. 888 888  888 "Y8888b. 888  888 888  888 888 888  888 888 888  888 .d888888 888  888
888"         888        X88 888 888 d88P      X88 888 888 d88P      X88 Y88b 888 888 d88P 888 Y88b 888 888 888 d88P 888  888 Y88..88P
888888888  8888888  88888P' 888 88888P"   88888P' 888 88888P"   88888P'  "Y88888 88888P"  888  "Y88888 888 88888P"  "Y888888  "Y88P"
                                888                   888
                                888                   888
                                888                   888
*
* */