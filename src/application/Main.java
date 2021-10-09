package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;



public class Main extends Application {
	
	public static Stage primaryStage;
	@Override
	public void start(Stage primaryStage) throws IOException {
		try {
	        Parent root = FXMLLoader.load(getClass().getResource("/application/Home.fxml"));
	        this.primaryStage = primaryStage;
	        primaryStage.setTitle("My Vocab");
	        primaryStage.setScene(new Scene(root));
	        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/myv.png")));
	        primaryStage.show();
			
			Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
//			primaryStage.setX(primaryScreenBounds.getMinX());
//			primaryStage.setY(primaryScreenBounds.getMinY());
//			primaryStage.setWidth(primaryScreenBounds.getWidth());
//			primaryStage.setHeight(primaryScreenBounds.getHeight());
			 
			 
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
