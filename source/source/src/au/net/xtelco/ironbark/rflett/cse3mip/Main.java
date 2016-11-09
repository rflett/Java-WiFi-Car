package au.net.xtelco.ironbark.rflett.cse3mip;

import static javafx.application.Application.launch;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * 
 * @author Ryan Flett
 */
public class Main extends Application {
    
    public static Stage stage;
    
    /**
     * Initiates the FXML window
     * 
     * @param stage of the FXML window
     * @throws Exception 
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/landing.fxml"));
        Main.stage = stage;

        Scene scene = new Scene(root);

        Main.stage.setScene(scene);
        Main.stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
