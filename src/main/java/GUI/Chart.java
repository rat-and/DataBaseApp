package GUI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class Chart extends Application {
    private SplitPane chartPane;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setAlwaysOnTop(true);
        Platform.setImplicitExit(false);

        primaryStage.setTitle("Twoj koszyk");
        initLayout();

        primaryStage.show();
    }

    private void initLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PriceComparerMain.class.getResource("/Chart.fxml"));
            chartPane = (SplitPane) loader.load();

            Scene scene = new Scene(chartPane);
            primaryStage.setScene(scene);
            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                   primaryStage.close();
                }
            });


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
