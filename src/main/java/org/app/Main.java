package org.app;

import javafx.application.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;

public class Main extends Application{

    //on program start, the authpanel is launched allowing the user to login
    @Override
    public void start(Stage primaryStage) {
        try {

            Parent root = FXMLLoader.load(getClass().getResource("/org/app/AuthPanel.fxml"));
            Scene scene = new Scene(root);

            primaryStage.setScene(scene);
            primaryStage.setTitle("User Authorization");
            primaryStage.setResizable(false);


            primaryStage.show();

            primaryStage.setOnCloseRequest( event -> {
                System.out.println("Closing CEC!");
                Platform.exit();
            });
        }
        catch (Throwable t){
            t.printStackTrace();
            try (PrintWriter out = new PrintWriter("error.log")) {
                t.printStackTrace(out);
            } catch (IOException ignored) {}

            t.printStackTrace();

            new Alert(Alert.AlertType.ERROR,
                    "Fatal startup error:\n" + t.getMessage()).showAndWait();
        }
    }
    public static void main(String[] args){
        launch(args);
    }
}