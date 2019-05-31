package application;
	
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
		    TextField inputFileName = new TextField();
		    TextField inputFolderName = new TextField();
		    TextField outputResults = new TextField();
		    Button searchButton = new Button("Search");
		    ProgressBar pb = new ProgressBar(0);
		    
		    Label labelFile = new Label("File:");
		    HBox hb1 = new HBox();
		    hb1.getChildren().addAll(labelFile, inputFileName);
		    hb1.setSpacing(10);
		    
		    Label labelFolder = new Label("Directory:");
            HBox hb2 = new HBox();
            hb2.getChildren().addAll(labelFolder, inputFolderName);
            hb2.setSpacing(10);
		    
		    Searcher3 searcher=new Searcher3();
		    
		    List<String> results = new ArrayList<>();
//            Thread thread = new Thread(new Runnable() {
//                @Override public void run() {
//                    int i = 0;
//                    while(i<10) {
//                    pb.setProgress(searcher.getProgressPercents()*100);
//                    try {
//                        Thread.sleep(10);
//                    }
//                    catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    i++;
//                    }
//                }
//            });
          
		    
		    EventHandler<ActionEvent> searchButtonHandler = new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                 
                 String startDirectory = inputFolderName.getText();
                 String fileName = inputFileName.getText();
                 try {
                    searcher.searchInDirectory(startDirectory, fileName, results, pb);
                    outputResults.setText(results.toString());
                }
                catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                 
                }
            };
            

            
            searchButton.setOnAction(searchButtonHandler);
		    
		    GridPane gridPane = new GridPane();
            gridPane.setVgap(5); 
            gridPane.setHgap(5);
            gridPane.setAlignment(Pos.TOP_CENTER); 
            
            gridPane.add(hb1, 0, 1);
            gridPane.add(hb2, 0, 2);
            gridPane.add(searchButton, 4, 2);
            gridPane.add(outputResults, 0, 3);
            gridPane.add(pb, 1, 5);
            
            
            Scene scene = new Scene(gridPane,280,350);
            primaryStage.setScene(scene);
            primaryStage.setTitle("BLABLA");
            primaryStage.show();;
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
