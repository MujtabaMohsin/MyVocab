package views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import javafx.event.ActionEvent;

import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import javafx.scene.control.ChoiceBox;

public class SettingsController implements Initializable {
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
	private ChoiceBox choice_box;
	
	@FXML
	private ChoiceBox choice_box2;
	
	@FXML
	private Button update_btn;

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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		setDefualtList();

	}

	public void setDefualtList() {
		ArrayList<ArrayList<String>> lists = new ArrayList<ArrayList<String>>();

		try {
			lists = data_retrieve.DBretrieve.getMyLists();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		choice_box2.getItems().add("Arabic");
		choice_box2.getSelectionModel().selectFirst();
 
		for (int i = 0; i < lists.get(1).size(); i++) {
			choice_box.getItems().add(lists.get(1).get(i));
		}

		String def_list_id = "";
		try {
			def_list_id = data_retrieve.DBretrieve.getDefualtList();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String def_list = "";

		for (int i = 0; i < lists.get(0).size(); i++) {

			if (lists.get(0).get(i).equals(def_list_id)) {
				def_list = lists.get(1).get(i);
			}

		}

		choice_box.getSelectionModel().select(def_list);

	}

	@FXML
	void updateSettings(ActionEvent event) throws SQLException {

		if (event.getSource() == update_btn) {

			String selected_list = choice_box.getSelectionModel().getSelectedItem().toString();

			ArrayList<ArrayList<String>> lists = new ArrayList<ArrayList<String>>();

			try {
				lists = data_retrieve.DBretrieve.getMyLists();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String new_def_list = "";

			for (int i = 0; i < lists.get(0).size(); i++) {

				if (selected_list.equals(lists.get(1).get(i))) {

					new_def_list = lists.get(0).get(i);

				}

			}
			
			data_retrieve.DBretrieve.updateSettings(new_def_list);
			
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Update");
			alert.setHeaderText("Update is done");
			 

			alert.showAndWait();
		}

	}
}