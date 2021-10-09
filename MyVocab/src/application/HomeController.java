package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class HomeController implements Initializable {

    @FXML
    private BorderPane main_pane;
    
    @FXML
    private Button mylists_btn;

    @FXML
    private Button test_btn;

    @FXML
    void open_mylists(ActionEvent event) throws IOException {
    	
    	if (event.getSource() == mylists_btn) {
//    		Parent list_pane = FXMLLoader.load(getClass().getResource(("../views/MyLists.fxml")));
// 
//    		 Main.primaryStage.setScene(new Scene(list_pane));
    		 
    		 FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/MyLists.fxml"));
    		 Parent root = loader.load();
    		 
             Main.primaryStage.setScene(new Scene(root));
		}
    	
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		data_retrieve.OxfordRetrieve.getJSONcode("access");
		
	}

}
