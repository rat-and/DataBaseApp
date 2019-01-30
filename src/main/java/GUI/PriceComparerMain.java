package GUI;

import Controllers.Observer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class PriceComparerMain extends Application {
    public Stage primatystage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primatystage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/Comparer.fxml"));
        primaryStage.setTitle("Price Comparer: " + Observer.users[Observer.priviliges]);
        primaryStage.setScene(new Scene(root, 1000, 800));

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                primaryStage.close();
            }
        });
        primaryStage.setOnShowing(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                primaryStage.setTitle("Price Comparer: " + Observer.users[Observer.priviliges]);
            }
        });
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}

