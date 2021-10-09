package views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import data_retrieve.DBretrieve;
import data_retrieve.WordModel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;

public class MyListsController implements Initializable {
	
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
	private TextField mylist_title;
	@FXML
	private Button newList_btn;
	@FXML
	private TableView<WordModel> list_table;
	@FXML
	private TableColumn<WordModel, Integer> col_num;
	@FXML
	private TableColumn<WordModel, String> col_lists;
	@FXML
	private TableColumn<WordModel, String> col_numWords;
	@FXML
	private TableColumn col_remove;

	// Event Listener on Button[#back_btn].onAction
	@FXML
	void back_home(ActionEvent event) throws IOException {
		if (event.getSource() == back_btn) {
			Parent home_pane = FXMLLoader.load(getClass().getResource(("/application/Home.fxml")));

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

	// Event Listener on Button[#newList_btn].onAction
	@FXML
	public void createList(ActionEvent event) throws SQLException, IOException {
		
		if (event.getSource() == newList_btn) {
			
	        TilePane tilePane = new TilePane();
	        // create a text input dialog 
	        TextInputDialog dialog = new TextInputDialog(); 
	        dialog.setTitle("Creat a new list");
	        dialog.setContentText("Set the new list's name:");
	        
	        Optional<String> result = dialog.showAndWait();
	        
	        String text_entered = dialog.getEditor().getText();
	        
	        //if ok is pressed
	        if (result.isPresent() && text_entered.trim().length() < 30 && text_entered.trim().length() > 0  ) {
	           data_retrieve.DBretrieve.addNewList(text_entered);

	           FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MyLists.fxml"));
	           Parent root = loader.load();  
	    		 
	            Main.primaryStage.setScene(new Scene(root,Main.primaryStage.getWidth()-14.40002441, Main.primaryStage.getHeight()-37.59997558));
	        } 
	    
	        
 
	        
		}
		 
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		populateTable();

	}

	public void populateTable() {

		ObservableList<WordModel> data = FXCollections.observableArrayList();

		ArrayList<ArrayList<String>> MylistsData = new ArrayList<ArrayList<String>>();

		try {
			MylistsData = DBretrieve.getMyLists();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < MylistsData.get(0).size(); i++) {

			// enter data

			String id = MylistsData.get(0).get(i);
			String name = MylistsData.get(1).get(i);

			data.add(new WordModel(id, i + 1, name, ""));

		}

		list_table.setItems(data);
		col_num.setCellValueFactory(new PropertyValueFactory<WordModel, Integer>("number"));
		col_lists.setCellValueFactory(new PropertyValueFactory<WordModel, String>("word"));
		col_numWords.setCellValueFactory(new PropertyValueFactory<WordModel, String>("result"));
		
		col_num.setStyle("-fx-font-size: 16px; -fx-alignment: CENTER;  -fx-font-weight:700;");
		col_lists.setStyle("-fx-font-size: 16px; -fx-alignment: CENTER;  -fx-font-weight:700;");
		col_numWords.setStyle("-fx-font-size: 16px; -fx-alignment: CENTER;  -fx-font-weight:700;");
		
		// When clicking on a row:
		list_table.setRowFactory(t -> {
			
			TableRow<WordModel> row = new TableRow<>();
			 
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty())) {

					WordModel rowData = row.getItem();

					loadSence(rowData.getId(), rowData.getWord());
					
				}
			});
			return row;
		});

	}

	public void loadSence(String id, String title) {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("List.fxml"));

		try {
			loader.load();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ListController listController = loader.<ListController>getController();

		listController.setListID(id, title);
		listController.initialize(null, null);
		Parent p = loader.getRoot();
		 
		Main.primaryStage.setScene(new Scene(p,Main.primaryStage.getWidth()-14.40002441, Main.primaryStage.getHeight()-37.59997558));
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
