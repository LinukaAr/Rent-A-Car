

/*
    @author DanujaV
    @created 9/2/23 - 11:38 AM
*/

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launcher extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/login_form.fxml"));

        Scene scene = new Scene(rootNode);

        stage.setScene(scene);
        stage.setTitle("Login Form");
        stage.centerOnScreen();

        stage.show();
    }
}
