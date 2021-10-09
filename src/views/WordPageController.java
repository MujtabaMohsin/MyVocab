package views;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.ResourceBundle;

import org.json.JSONObject;

import application.Main;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tab;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class WordPageController implements Initializable {

	boolean syn_open = false, forms_open = false;

	@FXML
	private WebView web;

	@FXML
	private Hyperlink oxford_hyp;
	
	@FXML
	private Hyperlink video_hyp;
	
	@FXML
	private Hyperlink camp_hyp;

	@FXML
	private Hyperlink camp_hyp_ar;

	@FXML
	private TextField search_txt;

	@FXML
	private ImageView logo;

	@FXML
	private Button search_btn;

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
	private Tab trans_examples_tab;

	@FXML
	private ScrollPane def_scroll;

	@FXML
	private VBox def_vbox;

	@FXML
	private VBox writing_vbox;

	@FXML
	private VBox dic_exmp_vbox;
	
	@FXML
	private VBox exmp_vbox;
	@FXML
	private VBox trans_ex_vbox;

	@FXML
	private VBox myexmp_vbox;

	@FXML
	private TextField myexm_txt;

	@FXML
	private Button myexm_add_btn;

	@FXML
	private ImageView listen;

	@FXML
	private VBox mytrans_vbox;

	@FXML
	private TextField mytrans_txt;

	@FXML
	private Button mytran_add_btn;

	@FXML
	private VBox trans_vbox;

	@FXML
	private VBox sysn_vbox;

	@FXML
	private VBox forms_vbox;

	@FXML
	private TextField writing_txt;

	@FXML
	private Button writing_btn;

	@FXML
	private Label writing_label;

	int num_of_myexamples = 0, num_of_translations = 0, num_of_writings = 0;

	public void setWord(String word_title) {
		this.word_title.setText(word_title);
	}

	public void initialize(URL arg0, ResourceBundle arg1) {

		if (!word_title.getText().equals("")) {

			examplesPage();
			definitonsPage();
			boolean isInMyList = false;
			//formsPage();
			 

			try {
				dictionaryExamplesPage();
				JSONObject json = data_retrieve.OxfordRetrieve.getJSONcode(word_title.getText(),"translations");
				
				translationsPage(json);
				translatedExamplesPage(json);
				showMyExamples();
				
				String def_list_id = data_retrieve.DBretrieve.getDefualtList();
				isInMyList = data_retrieve.DBretrieve.checkWordInList(word_title.getText(),def_list_id);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			if (!isInMyList) {
				add_btn.setText("Add");
				add_btn.setStyle("-fx-background-color: #4169E1 ; -fx-border-color: black; -fx-border-width:0.5");
			} else {
				add_btn.setText("Remove");
				add_btn.setStyle("-fx-background-color: red ; -fx-border-color: black; -fx-border-width:0.5");
			}

			syn_tab.setOnSelectionChanged(event -> {
				if (syn_tab.isSelected() && !syn_open) {
					syn_open = true;
					synonymsPage();
				}
			});

		    forms_tab.setOnSelectionChanged(event -> {
		        if (forms_tab.isSelected() && !forms_open) {
		        	forms_open = true;
		            formsPage();
		        }
		    });

		}

	}

	public void showMyExamples() throws SQLException {

		ArrayList<String> examples = new ArrayList<String>();
		examples = data_retrieve.DBretrieve.getMyExamples(word_title.getText());

		for (int i = 0; i < examples.size(); i++) {
			HBox hbox = new HBox();
			hbox = creatHBox(i, examples.get(i), "example");
			myexmp_vbox.getChildren().add(hbox);
			num_of_myexamples++;
		}
		// swap
		ObservableList<Node> workingCollection = FXCollections.observableArrayList(myexmp_vbox.getChildren());

		for (int i = 1; i < myexmp_vbox.getChildren().size() - 1; i++) {
			Collections.swap(workingCollection, i, i + 1);
		}

		myexmp_vbox.getChildren().setAll(workingCollection);

	}

	public void showMyTranslations() throws SQLException {

		ArrayList<String> translations = new ArrayList<String>();
		translations = data_retrieve.DBretrieve.getMyTranslations(word_title.getText());

		for (int i = 0; i < translations.size(); i++) {
			HBox hbox = new HBox();
			hbox = creatHBox(i, translations.get(i), "mytrans");
			// hbox.setMargin(mytrans_vbox, new Insets(0, 0, 0, 27));
			mytrans_vbox.getChildren().add(hbox);
			num_of_translations++;
		}
		// swap
		ObservableList<Node> workingCollection = FXCollections.observableArrayList(mytrans_vbox.getChildren());

		for (int i = 1; i < mytrans_vbox.getChildren().size() - 1; i++) {
			Collections.swap(workingCollection, i, i + 1);
		}

		mytrans_vbox.getChildren().setAll(workingCollection);

	}

	@FXML
	void addMyExample(ActionEvent event) throws SQLException {

		if (event.getSource() == myexm_add_btn && !(myexm_txt.getText().trim().equals(""))) {

			data_retrieve.DBretrieve.addNewExample(word_title.getText(), myexm_txt.getText());
			HBox hbox = new HBox();
			hbox = creatHBox(num_of_myexamples, myexm_txt.getText(), "sub");
			myexmp_vbox.getChildren().add(hbox);
			num_of_myexamples++;
			// swap
			ObservableList<Node> workingCollection = FXCollections.observableArrayList(myexmp_vbox.getChildren());

			Collections.swap(workingCollection, myexmp_vbox.getChildren().size() - 2,
					myexmp_vbox.getChildren().size() - 1);

			myexmp_vbox.getChildren().setAll(workingCollection);

		}

	}

	@FXML
	void addMyTranslation(ActionEvent event) throws SQLException {

		if (event.getSource() == mytran_add_btn && !(mytrans_txt.getText().trim().equals(""))) {

			data_retrieve.DBretrieve.addNewTranslation(word_title.getText(), mytrans_txt.getText());
			HBox hbox = new HBox();
			hbox = creatHBox(num_of_translations, mytrans_txt.getText(), "mytrans");
			mytrans_vbox.getChildren().add(hbox);

			// swap
			ObservableList<Node> workingCollection = FXCollections.observableArrayList(mytrans_vbox.getChildren());

			Collections.swap(workingCollection, mytrans_vbox.getChildren().size() - 2,
					mytrans_vbox.getChildren().size() - 1);

			mytrans_vbox.getChildren().setAll(workingCollection);

		}

	}

	public void setDictionryWebView() {

		WebEngine webEngine = web.getEngine();
		webEngine.load("http://www.oracle.com/products/index.html");

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
	void back_method(ActionEvent event) throws IOException, SQLException {

		if (event.getSource() == back_btn) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("List.fxml"));

			try {
				loader.load();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			ListController listController = loader.<ListController>getController();
			String def_list_id = data_retrieve.DBretrieve.getDefualtList();
			listController.setListID(def_list_id, "My List");
			listController.initialize(null, null);
			Parent p = loader.getRoot();

			Main.primaryStage.setScene(new Scene(p, Main.primaryStage.getWidth() - 14.40002441,
					Main.primaryStage.getHeight() - 37.59997558));
		}

	}

	public void examplesPage() {

		ArrayList<String> fraze_data = new ArrayList<String>();

		try {
			fraze_data = word_tabs.Exmples.examples_data(word_title.getText());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < fraze_data.size(); i++) {
			HBox hbox = new HBox();
			hbox = creatHBox(i, fraze_data.get(i), "example");

			exmp_vbox.getChildren().add(hbox);
		}

	}

	public void formsPage() {
		 
		ArrayList<String> fraze_data = new ArrayList<String>();
		try {
			fraze_data = word_tabs.forms.forms_data(word_title.getText());
			//System.out.println(fraze_data);
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

	public void translationsPage(JSONObject json) throws SQLException {

		showMyTranslations();

		ArrayList<ArrayList<String>> fraze_data = new ArrayList<ArrayList<String>>();
		fraze_data.clear();
		try {
			 

			fraze_data = word_tabs.Translations.getTranslations(json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String traslations = "";

		for (int i = 0; i < fraze_data.get(0).size(); i++) {
			if (i != 0) {
				traslations = traslations + ", " + fraze_data.get(0).get(i);
			} else {
				traslations = fraze_data.get(0).get(i);
			}

		}
		word_trans.setText(traslations);

		for (int i = 0; i < fraze_data.get(0).size(); i++) {

			HBox hbox = new HBox();
			hbox = creatHBox(i, fraze_data.get(0).get(i), "trans");
			hbox.setMargin(trans_vbox, new Insets(0, 0, 0, 22));
			trans_vbox.getChildren().add(hbox);
		}

	}

	public void translatedExamplesPage(JSONObject json) throws SQLException {

		ArrayList<ArrayList<String>> fraze_data = new ArrayList<ArrayList<String>>();
		fraze_data.clear();
		try {
			

			fraze_data = word_tabs.Translations.getTranslations(json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (fraze_data.get(1).size() > fraze_data.get(2).size() ) {
			
			int diff = fraze_data.get(1).size() - fraze_data.get(2).size();
			for (int i = 0; i < diff; i++) {
				fraze_data.get(2).add("");
			}
		}
		
		else if (fraze_data.get(1).size() < fraze_data.get(2).size()) {
			int diff = fraze_data.get(2).size() - fraze_data.get(1).size();
			for (int i = 0; i < diff; i++) {
				fraze_data.get(1).add("");
			}
		}

		for (int i = 0; i < fraze_data.get(1).size(); i++) {

			HBox hbox = new HBox();
			 
			String text = fraze_data.get(1).get(i) + ", " + fraze_data.get(2).get(i);
			 
			
			hbox = creatHBox(i, text, "example");
			hbox.setMargin(trans_vbox, new Insets(0, 0, 0, 22));
			trans_ex_vbox.getChildren().add(hbox);
		}

	}

	public void definitonsPage() {

		ArrayList<HashMap<String, Object>> fraze_data = new ArrayList<HashMap<String, Object>>();
		try {
			fraze_data = word_tabs.Definitions.definitions_data(word_title.getText());
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
	
	
	public void dictionaryExamplesPage() {


		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();

		try {
			JSONObject json = data_retrieve.OxfordRetrieve.getJSONcode(word_title.getText(),"sentences");

			data = word_tabs.DictionaryExamples.getDicExamples(json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < data.size(); i++) {

			HBox hbox = new HBox();
			String type = " • (" + data.get(i).get("type").toString() + ")";
			hbox = creatHBox(i, type.toLowerCase(), "main");

			dic_exmp_vbox.getChildren().add(hbox);

			ArrayList<String> temp_arr = new ArrayList<String>();
			temp_arr = (ArrayList<String>) data.get(i).get("examples");

			for (int j = 0; j < temp_arr.size(); j++) {

				HBox hbox2 = new HBox();
				String text = temp_arr.get(j);
				hbox2 = creatHBox(j, text, "example");
				dic_exmp_vbox.getChildren().add(hbox2);

			}

		}

	
	}

	public void synonymsPage() {

		ArrayList<HashMap<String, Object>> fraze_data = new ArrayList<HashMap<String, Object>>();
		try {
			fraze_data = word_tabs.Synonyms.synonyms_data(word_title.getText());

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
		if (type.equals("sub") || type.equals("example")) {
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

		if (type.equals("sub") || type.equals("trans") || type.equals("example")) {

			num.setText("" + (i + 1) + ".");

			if (type.equals("trans")) {
				num.setPrefWidth(100);
				num.setAlignment(Pos.CENTER_RIGHT);
			} else {
				num.setPrefWidth(50);
			}
			num.setPrefHeight(35);
			num.setAlignment(Pos.CENTER_RIGHT);

		}

		else if (type.equals("mytrans")) {

			num.setText("." + (i + 1));

			num.setPrefWidth(50);
			num.setPrefHeight(35);
			num.setAlignment(Pos.CENTER_RIGHT);

		}

		if (type.equals("sub") || type.equals("example") || type.equals("mytrans") || type.equals("trans")) {
			TextArea textA = new TextArea();
			textA.setWrapText(true);

			if (type.equals("sub") || type.equals("example")) {
				textA.setPrefSize(1050, 49);
			} else {
				textA.setPrefSize(510, 49);
			}

			textA.setEditable(false);
			textA.getStylesheets().add("/views/textAreaStyle.css");

			textA.setText(text);
			if (type.equals("example")) {
				textA.setStyle("-fx-highlight-fill: yellow; -fx-highlight-text-fill: black;");
				int st = getStartWord(textA.getText(), word_title.getText());
				int end = getEndWord(textA.getText(), word_title.getText());
				textA.setOnMouseExited((event) -> {

					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							textA.selectRange(st, end);
						}
					});
				});
				
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						textA.selectRange(st, end);
					}
				});
			} 
			

			

			setFonts(num, textA, null);
			hbox.getChildren().add(num);
			hbox.getChildren().add(textA);
//			if (!(type.equals("trans"))) {
			hbox.setHgrow(textA, Priority.ALWAYS);
//			}

		}

		else {
			Label main_title = new Label();
			//main_title.setPrefWidth(150);
			main_title.setPrefHeight(44);
			main_title.setText(text);
			// main_title.mar

			setFonts(null, null, main_title);
			main_title.setStyle("-fx-background-color: orange;");

			hbox.getChildren().add(main_title);
			hbox.setHgrow(main_title, Priority.ALWAYS);

		}

		return hbox;

	}
	
	public int getStartWord(String text, String word) {

		int start = 0;
 

		start = text.toLowerCase().indexOf(word);

		return start;
	}

	public int getEndWord(String text, String word) {

		int end = 0;

		int start = getStartWord(text.toLowerCase(), word);
		
		if (start == -1) {
			return -1;
		}
		
		else {
		
		for (int i = start; i < text.length() ; i++) {
			end = i;
			if(String.valueOf(text.charAt(i)).equals(" ") || String.valueOf(text.charAt(i)).equals(",") || String.valueOf(text.charAt(i)).equals(".") ) {
				break;
			}
			
			
		}
		}

		return end;
	}

	@FXML
	void addToMylist(ActionEvent event) throws SQLException {

		if (event.getSource() == add_btn) {
			
			String def_list_id = data_retrieve.DBretrieve.getDefualtList();

			boolean isInMyList = data_retrieve.DBretrieve.checkWordInList(word_title.getText(),def_list_id);
			if (!isInMyList) {
				data_retrieve.DBretrieve.addToMyList(word_title.getText(),def_list_id);
				add_btn.setText("Remove");
				add_btn.setStyle("-fx-background-color: red ; -fx-border-color: black; -fx-border-width:0.5");
			}

			else {
				data_retrieve.DBretrieve.deleteFromMyList(word_title.getText(),def_list_id);
				add_btn.setText("Add");
				add_btn.setStyle("-fx-background-color: #4169E1 ; -fx-border-color: black; -fx-border-width:0.5");

			}

		}

	}

	@FXML
	void goHome(MouseEvent event) throws IOException {

		if (event.getSource() == logo) {

			Parent home_pane = FXMLLoader.load(getClass().getResource(("/application/Home.fxml")));

			Main.primaryStage.setScene(new Scene(home_pane, Main.primaryStage.getWidth() - 14.40002441,
					Main.primaryStage.getHeight() - 37.59997558));

		}
	}

	@FXML
	void checkWriting(ActionEvent event) throws SQLException {

		if (event.getSource() == writing_btn) {

			if (word_title.getText().equals(writing_txt.getText().toLowerCase().trim())) {
				num_of_writings++;
				writing_label.setVisible(false);
				HBox hbox = new HBox();
				hbox = creatHBox(num_of_writings - 1, writing_txt.getText().trim(), "sub");
				writing_vbox.getChildren().add(hbox);

				// swap
				ObservableList<Node> workingCollection = FXCollections.observableArrayList(writing_vbox.getChildren());

				Collections.swap(workingCollection, writing_vbox.getChildren().size() - 2,
						writing_vbox.getChildren().size() - 1);

				writing_vbox.getChildren().setAll(workingCollection);

				writing_txt.setText("");
			}

			else {
				writing_label.setVisible(true);
			}

		}
	}

	@FXML
	void listenMethod(MouseEvent event) {

		if (event.getSource() == listen) {
			Media sound = new Media(
					"https://audio.oxforddictionaries.com/en/mp3/" + word_title.getText() + "_gb_1.mp3");
			MediaPlayer mediaPlayer = new MediaPlayer(sound);
			mediaPlayer.play();

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

		Main.primaryStage.setScene(
				new Scene(p, Main.primaryStage.getWidth() - 14.40002441, Main.primaryStage.getHeight() - 37.59997558));

	}

	// THIS METHOD IS REPEATED
	@FXML
	void enterPressed(KeyEvent event) throws SQLException {

		if (event.getCode().equals(KeyCode.ENTER) && event.getSource() == writing_txt) {

			if (word_title.getText().equals(writing_txt.getText().toLowerCase().trim())) {
				num_of_writings++;
				writing_label.setVisible(false);
				HBox hbox = new HBox();
				hbox = creatHBox(num_of_writings - 1, writing_txt.getText().trim(), "sub");
				writing_vbox.getChildren().add(hbox);

				// swap
				ObservableList<Node> workingCollection = FXCollections.observableArrayList(writing_vbox.getChildren());

				Collections.swap(workingCollection, writing_vbox.getChildren().size() - 2,
						writing_vbox.getChildren().size() - 1);

				writing_vbox.getChildren().setAll(workingCollection);

				writing_txt.setText("");
			}

			else {
				writing_label.setVisible(true);
			}

		}

		else if (event.getCode().equals(KeyCode.ENTER) && event.getSource() == mytrans_txt
				&& !(mytrans_txt.getText().trim().equals(""))) {

			data_retrieve.DBretrieve.addNewTranslation(word_title.getText(), mytrans_txt.getText());
			HBox hbox = new HBox();
			hbox = creatHBox(num_of_translations, mytrans_txt.getText(), "mytrans");
			mytrans_vbox.getChildren().add(hbox);

			// swap
			ObservableList<Node> workingCollection = FXCollections.observableArrayList(mytrans_vbox.getChildren());

			Collections.swap(workingCollection, mytrans_vbox.getChildren().size() - 2,
					mytrans_vbox.getChildren().size() - 1);

			mytrans_vbox.getChildren().setAll(workingCollection);
		}

		else if (event.getCode().equals(KeyCode.ENTER) && event.getSource() == myexm_txt
				&& !(myexm_txt.getText().trim().equals(""))) {

			data_retrieve.DBretrieve.addNewExample(word_title.getText(), myexm_txt.getText());
			HBox hbox = new HBox();
			hbox = creatHBox(num_of_myexamples, myexm_txt.getText(), "sub");
			myexmp_vbox.getChildren().add(hbox);
			num_of_myexamples++;
			// swap
			ObservableList<Node> workingCollection = FXCollections.observableArrayList(myexmp_vbox.getChildren());

			Collections.swap(workingCollection, myexmp_vbox.getChildren().size() - 2,
					myexmp_vbox.getChildren().size() - 1);

			myexmp_vbox.getChildren().setAll(workingCollection);
		}

	}

	public String createLink(String website, String word) {

		
		if (word.contains(" ")) {
			word = word.replaceAll(" ", "+");
		}
		
		String link = "";

		if (website.equals("oxford")) {
			link = "https://www.oxfordlearnersdictionaries.com/definition/english/" + word;
		}

		else if (website.equals("camp")) {
			link = "https://dictionary.cambridge.org/dictionary/english/" + word;
		}

		else if (website.equals("camp_ar")) {
			link = "https://dictionary.cambridge.org/dictionary/english-arabic/" + word;
		}

		else if (website.equals("video")) {
			link = "https://youglish.com/pronounce/" + word + "/english?";
		}

		return link;

	}

	@FXML
	void openLink(ActionEvent event) {

		if (event.getSource() == oxford_hyp) {

			String link = createLink("oxford", word_title.getText());
			try {
				Desktop.getDesktop().browse(new URL(link).toURI());
			} catch (IOException | URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		else if (event.getSource() == camp_hyp) {

			String link = createLink("camp", word_title.getText());
			try {
				Desktop.getDesktop().browse(new URL(link).toURI());
			} catch (IOException | URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		else if (event.getSource() == camp_hyp_ar) {

			String link = createLink("camp_ar", word_title.getText());
			try {
				Desktop.getDesktop().browse(new URL(link).toURI());
			} catch (IOException | URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		else if (event.getSource() == video_hyp) {

			String link = createLink("video", word_title.getText());
			try {
				Desktop.getDesktop().browse(new URL(link).toURI());
			} catch (IOException | URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		

	}
	
	
	

}
