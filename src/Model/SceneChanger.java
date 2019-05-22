
package Model;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Controller.ControllerInterface;

public class SceneChanger {
    
    /*
    * This method changes scenes by accepting the file path of the .fxml file as a string,
    * and the title of the scene as a string. 
    */
    public void changeScenes(ActionEvent event, String viewName, String title) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(viewName));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }
    
    /*
    * This method will change scenes, passing the user object to the next scene.
    
    */
    public void changeScenes(ActionEvent event, String viewName, String title, User user, ControllerInterface controller) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(viewName));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        controller = loader.getController(); 
        controller.preloadData(user);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }
}
