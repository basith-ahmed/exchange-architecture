import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TradingDashboard extends Application {
    @Override
    public void start(Stage primaryStage) {
        Label label = new Label("Trading Dashboard");
        VBox root = new VBox(label);

        Scene scene = new Scene(root, 400, 300);

        primaryStage.setTitle("Trading Dashboard");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
