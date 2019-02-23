package small_management_program;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;


public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        System.out.print("ciao");
        FXMLLoader loader = new FXMLLoader(new File("C:\\Users\\Michele\\myfiles\\repository\\small_management_program\\src\\main\\resources\\sample.fxml").toURI().toURL());
        System.out.print(loader.toString());
        Parent root = loader.load();
        Scene scene = new Scene(root);
        String css = this.getClass().getResource("/style.css").toString();
        System.out.print(css);
        scene.getStylesheets().add(css);

        stage.setTitle("JavaFX and Gradle");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}