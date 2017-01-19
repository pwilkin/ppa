package org.ilintar.study;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

import java.net.URL;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			URL res = getClass().getResource("StudyMainScreen.fxml");
			System.out.println(res);
			BorderPane root = (BorderPane)FXMLLoader.load(res);
			Scene scene = new Scene(root,600,300);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
