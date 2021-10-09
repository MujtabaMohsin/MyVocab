package application;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;

import org.json.JSONObject;

import data_retrieve.FrazeRetrieve;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import quiz.TakeQuiz;
import quiz.quizController;
import views.WordPageController;

public class HomeController implements Initializable {
	@FXML
	private TextField search_txt;

	@FXML
	private Button search_btn;

	@FXML
	private ImageView logo;
	
	@FXML
	private HBox hbox;
	
    @FXML
    private Button speaking_btn;

    @FXML
    private Button settings_btn;
	

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

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MyLists.fxml"));
			Parent root = loader.load();
		 
			Main.primaryStage.setScene(new Scene(root,Main.primaryStage.getWidth()-14.40002441, Main.primaryStage.getHeight()-37.59997558));
		}

	}
	

    @FXML
    void openTest(ActionEvent event) throws IOException {
    	if (event.getSource() == test_btn) {
    		
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/quiz/quiz.fxml"));
    		Parent root = loader.load();
			 
			Main.primaryStage.setScene(new Scene(root,Main.primaryStage.getWidth()-14.40002441, Main.primaryStage.getHeight()-37.59997558));
    		
    		
    	}
    }
    
    
    @FXML
    void openSettings (ActionEvent event) throws IOException {
    	if (event.getSource() == settings_btn) {
    		
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Settings.fxml"));
    		Parent root = loader.load();
			 
			Main.primaryStage.setScene(new Scene(root,Main.primaryStage.getWidth()-14.40002441, Main.primaryStage.getHeight()-37.59997558));
    		
    		
    	}
    }
    
    
    @FXML
    void openSpeaking (ActionEvent event) throws IOException {
    	if (event.getSource() == speaking_btn) {
    		
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Speaking.fxml"));
    		Parent root = loader.load();
			 
			Main.primaryStage.setScene(new Scene(root,Main.primaryStage.getWidth()-14.40002441, Main.primaryStage.getHeight()-37.59997558));
    		
    		
    	}
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
 
	}

	@FXML
	void wordSearch(ActionEvent event) {

		if (event.getSource() == search_btn && search_txt.getText().length() > 1) {
			searchMethod();
		}

	}

	@FXML
	void searchByKeyboard(KeyEvent event) {

		if (event.getSource() == search_txt && event.getCode().equals(KeyCode.ENTER)) {
			searchMethod();
		}

	}
	
	public void searchMethod() {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/WordPage.fxml"));

		try {
			loader.load();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		WordPageController wordPageController = loader.<WordPageController>getController();
		 
		String word = search_txt.getText().trim();
		wordPageController.setWord(word);
		wordPageController.initialize(null, null);
		Parent p = loader.getRoot();
		
		Main.primaryStage.setScene(new Scene(p,Main.primaryStage.getWidth()-14.40002441, Main.primaryStage.getHeight()-37.59997558));
		
	}

}