package views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

import application.Main;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;

import javafx.scene.control.TextArea;

import javafx.scene.layout.AnchorPane;

import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;

import javafx.scene.layout.BorderPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class SpeakingController implements Initializable {
	
	ArrayList<String> all_questions = new ArrayList<String>();
	
	@FXML
	private BorderPane main_pane;
	@FXML
	private ImageView logo;
	@FXML
	private TextField search_txt;
	@FXML
	private Button search_btn;
	@FXML
	private Button back_btn;
	@FXML
	private TextField mylist_title;
	@FXML
	private AnchorPane startQuiz_pane;
	@FXML
	private TextArea qus_txt;
	@FXML
	private Button next_btn;
	@FXML
	private Button nextQ_btn;

	// Event Listener on ImageView[#logo].onMouseClicked
	@FXML
	void back_home(ActionEvent event) throws IOException {
		if (event.getSource() == back_btn) {
			Parent home_pane = FXMLLoader.load(getClass().getResource(("/application/Home.fxml")));

			Main.primaryStage.setScene(new Scene(home_pane, Main.primaryStage.getWidth() - 14.40002441,
					Main.primaryStage.getHeight() - 37.59997558));
		}
	}

	@FXML
	void goHome(MouseEvent event) throws IOException {

		if (event.getSource() == logo) {

			Parent home_pane = FXMLLoader.load(getClass().getResource(("/application/Home.fxml")));

			Main.primaryStage.setScene(new Scene(home_pane,Main.primaryStage.getWidth()-14.40002441, Main.primaryStage.getHeight()-37.59997558));

		}
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

	@FXML
	public void goNextQuestion(ActionEvent event) {
		if (event.getSource() == next_btn) {
			all_questions.add(all_questions.get(0));
			all_questions.remove(0);
			qus_txt.setText(all_questions.get(0));
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		qus_txt.getStylesheets().add("/views/textAreaSpeakingStyle.css");
		PseudoClass centered = PseudoClass.getPseudoClass("centered");
		qus_txt.pseudoClassStateChanged(centered, true);
		
		try {
			all_questions = getSpeakingQuestions();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		qus_txt.setText(all_questions.get(0));

	}

	public ArrayList<String> getSpeakingQuestions() throws SQLException {

		ArrayList<String> questions = new ArrayList<String>();
		questions = data_retrieve.DBretrieve.getSpeakingQuestions();

		ArrayList<String> questions_random = new ArrayList<String>();

		Random rand = new Random();
		int num_of_questions = questions.size();

		for (int i = 0; i < num_of_questions; i++) {
			int curr_random = rand.nextInt(questions.size());
			questions_random.add(questions.get(curr_random));
			questions.remove(curr_random);
			
		}

		return questions_random;

	}

}
