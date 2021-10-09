package views;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;

import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tab;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class WordPageController implements Initializable {
	@FXML
	private BorderPane main_pane;
	@FXML
	private Button back_btn;
	@FXML
	private TextField word_title;
	@FXML
	private TextField word_trans;
	@FXML
	private Button add_btn;
	@FXML
	private TabPane tab_pane;
	@FXML
	private Tab main_tab;
	@FXML
	private Tab examples_tab;
	@FXML
	private Tab my_examples_tab;
	@FXML
	private Tab translations_tab;
	@FXML
	private Tab syn_tab;
	@FXML
	private Tab forms_tab;
	@FXML
	private Tab dic_tab;
	@FXML
	private Tab writing_tab;
	@FXML
	private Tab video_tab;

	@FXML
	private ScrollPane def_scroll;

	@FXML
	private VBox def_vbox;

	@FXML
	private VBox exmp_vbox;

	@FXML
	private VBox myexmp_vbox;

	@FXML
	private VBox trans_vbox;

	@FXML
	private VBox sysn_vbox;

	@FXML
	private VBox forms_vbox;

	public void setWord(String word_title) {
		this.word_title.setText(word_title);
	}

	public void initialize(URL arg0, ResourceBundle arg1) {

		if (!word_title.getText().equals("")) {

			examplesPage();
			definitonsPage();
			formsPage();
			synonymsPage();
		}

	}

	public void setFonts(Label label, TextArea text, Label main_title) {

		Font font1 = Font.font("System", FontWeight.BOLD, 20);
		Font font2 = Font.font("Verdana", 20);

		if (text != null) {
			label.setTextFill(Color.BLACK);
			label.setFont(font1);
			text.setFont(font2);
		}

		else {

			main_title.setFont(font2);

		}

	}

	@FXML
	void back_method(ActionEvent event) throws IOException {

		if (event.getSource() == back_btn) {
			Parent home_pane = FXMLLoader.load(getClass().getResource(("List.fxml")));

			Main.primaryStage.setScene(new Scene(home_pane));
		}

	}

	public void examplesPage() {

		ArrayList<String> fraze_data = new ArrayList<String>();
		try {
			fraze_data = data_retrieve.FrazeRetrieve.examples_data(word_title.getText());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < fraze_data.size(); i++) {
			HBox hbox = new HBox();
			hbox = creatHBox(i, fraze_data.get(i), "sub");

			exmp_vbox.getChildren().add(hbox);
		}

	}

	public void formsPage() {

		ArrayList<String> fraze_data = new ArrayList<String>();
		try {
			fraze_data = data_retrieve.FrazeRetrieve.forms_data(word_title.getText());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < fraze_data.size(); i++) {
			HBox hbox = new HBox();
			hbox = creatHBox(i, fraze_data.get(i), "sub");

			forms_vbox.getChildren().add(hbox);
		}

	}

	public void definitonsPage() {

		ArrayList<HashMap<String, Object>> fraze_data = new ArrayList<HashMap<String, Object>>();
		try {
			fraze_data = data_retrieve.FrazeRetrieve.definitions_data(word_title.getText());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < fraze_data.size(); i++) {

			HBox hbox = new HBox();
			String type = " • (" + fraze_data.get(i).get("type").toString() + ")";
			hbox = creatHBox(i, type.toLowerCase(), "main");

			def_vbox.getChildren().add(hbox);

			ArrayList<String> temp_arr = new ArrayList<String>();
			temp_arr = (ArrayList<String>) fraze_data.get(i).get("defs");

			for (int j = 0; j < temp_arr.size(); j++) {

				HBox hbox2 = new HBox();
				String text = temp_arr.get(j);
				hbox2 = creatHBox(j, text, "sub");
				def_vbox.getChildren().add(hbox2);

			}

		}

	}

	public void synonymsPage() {

		ArrayList<HashMap<String, Object>> fraze_data = new ArrayList<HashMap<String, Object>>();
		try {
			fraze_data = data_retrieve.FrazeRetrieve.synonyms_data(word_title.getText());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < fraze_data.size(); i++) {

			HBox hbox = new HBox();
			String type = " • (" + fraze_data.get(i).get("type").toString() + ")";
			hbox = creatHBox(i, type.toLowerCase(), "main");

			sysn_vbox.getChildren().add(hbox);

			ArrayList<String> temp_arr = new ArrayList<String>();
			temp_arr = (ArrayList<String>) fraze_data.get(i).get("syns");

			for (int j = 0; j < temp_arr.size(); j++) {

				HBox hbox2 = new HBox();
				String text = temp_arr.get(j);
				hbox2 = creatHBox(j, text, "sub");
				sysn_vbox.getChildren().add(hbox2);

			}

		}

	}

	public HBox creatHBox(int i, String text, String type) {

		HBox hbox = new HBox();
		hbox.setFillHeight(true);
		if (type.equals("sub")) {
			hbox.setPrefSize(1100, 44);
		} else {
			hbox.setPrefHeight(70);
		}

		hbox.setStyle("-fx-background-color: #f2f2f4");
//		if ((i + 1) % 2 != 0 || type.equals("main")) {
//			hbox.setStyle("-fx-background-color: #ffffff");
//		} else {
//			hbox.setStyle("-fx-background-color: #f2f2f4");
//		}

		Label num = new Label();

		if (type.equals("sub")) {

			num.setText("" + (i + 1) + ".");

			num.setPrefWidth(50);
			num.setPrefHeight(35);
			num.setAlignment(Pos.CENTER_RIGHT);

		}

		if (type.equals("sub")) {
			TextArea textA = new TextArea();
			textA.setWrapText(true);

			textA.setPrefSize(1050, 49);

			textA.setEditable(false);
			textA.getStylesheets().add("/views/textAreaStyle.css");

			textA.setText(text);

			setFonts(num, textA, null);
			hbox.getChildren().add(num);
			hbox.getChildren().add(textA);
			hbox.setHgrow(textA, Priority.ALWAYS);
		}

		else {
			Label main_title = new Label();
			main_title.setPrefWidth(150);
			main_title.setPrefHeight(44);
			main_title.setText(text);
			// main_title.mar

			setFonts(null, null, main_title);

			hbox.getChildren().add(main_title);
			hbox.setHgrow(main_title, Priority.ALWAYS);

		}

		return hbox;

	}

}
