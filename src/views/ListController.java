package views;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.Main;
import data_retrieve.DBretrieve;
import data_retrieve.WordModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class ListController implements Initializable {
    @FXML
    private TextField search_txt;
    
    @FXML
    private ImageView logo;
    @FXML
    private Button search_btn;

	@FXML
	public Label list_id;

	@FXML
	private TextField seach_txt;

	@FXML
	public Label list_title;

	@FXML
	private BorderPane main_pane;

	@FXML
	private TableView<WordModel> list_table;

	@FXML
	private TableColumn<WordModel, Integer> col_num;

	@FXML
	private TableColumn<WordModel, String> col_vocab;

	@FXML
	private TableColumn<WordModel, String> col_result;

	@FXML
	private TableColumn<?, ?> col_remove;

	@FXML
	private Button back_btn;

	@FXML
	void back_home(ActionEvent event) throws IOException {

		if (event.getSource() == back_btn) {
			Parent home_pane = FXMLLoader.load(getClass().getResource(("/views/MyLists.fxml")));

			Main.primaryStage.setScene(new Scene(home_pane,Main.primaryStage.getWidth()-14.40002441, Main.primaryStage.getHeight()-37.59997558));
		}

	}
	
	
    @FXML
    void goHome(MouseEvent event) throws IOException {

    	if (event.getSource() == logo) {
    		
			Parent home_pane = FXMLLoader.load(getClass().getResource(("/application/Home.fxml")));

			Main.primaryStage.setScene(new Scene(home_pane,Main.primaryStage.getWidth()-14.40002441, Main.primaryStage.getHeight()-37.59997558));
    		
    	}
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		 
		populateTable();

	}

	public void populateTable() {

		ObservableList<WordModel> data = FXCollections.observableArrayList();

		ArrayList<ArrayList<String>> MylistData = new ArrayList<ArrayList<String>>();

		try {
			 
			MylistData = DBretrieve.getMyWordList(list_id.getText());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < MylistData.get(0).size(); i++) {

			// enter data
			String id = MylistData.get(0).get(i);
			String word = MylistData.get(1).get(i);
			String result = MylistData.get(2).get(i);

			data.add(new WordModel(id, i + 1, word, result));

		}

		list_table.setItems(data);
		col_num.setCellValueFactory(new PropertyValueFactory<WordModel, Integer>("number"));
		col_vocab.setCellValueFactory(new PropertyValueFactory<WordModel, String>("word"));
		col_result.setCellValueFactory(new PropertyValueFactory<WordModel, String>("result"));

		col_num.setStyle("-fx-font-size: 18px; -fx-alignment: CENTER;  -fx-font-weight:600;");
		col_vocab.setStyle("-fx-font-size: 18px; -fx-alignment: CENTER;  -fx-font-weight:700;");
		col_result.setStyle("-fx-font-size: 18px; -fx-alignment: CENTER;  -fx-font-weight:600;");
		
		
		// When clicking on a row:
		list_table.setRowFactory(t1 -> {
			TableRow<WordModel> row1 = new TableRow<>();
			row1.setOnMouseClicked(event1 -> {
				if (event1.getClickCount() == 2 && (!row1.isEmpty())) {

					WordModel rowData = row1.getItem();

					loadSence(rowData.getWord());

				}
			});
			return row1;
		});

	}

	public void loadSence(String word) {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("WordPage.fxml"));

		try {
			loader.load();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		WordPageController wordPageController = loader.<WordPageController>getController();
		 
		wordPageController.setWord(word);
		wordPageController.initialize(null, null);
		Parent p = loader.getRoot();

		Main.primaryStage.setScene(new Scene(p,Main.primaryStage.getWidth()-14.40002441, Main.primaryStage.getHeight()-37.59997558));
	}

	public void setListID(String list_id, String list_title) {
		this.list_id.setText(list_id);
		this.list_title.setText(list_title);

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
