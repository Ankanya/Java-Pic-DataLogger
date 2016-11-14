package application;
	
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;


public class volkan extends Application {
	

	private static ExecutorService executorService;
	
	public static ProgressBar sProgressBar;
	
	@FXML
	public ProgressBar bershmlug;
	

	@FXML
	 public void start(Stage primaryStage) throws Exception{

		   FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
				   "alfa.fxml"));
				           fxmlLoader.setController(this);
				           try {
				              Parent root =  fxmlLoader.load();
				            
				      		
				      		Callable<Boolean> ro = ()->{
				      			SerialTest st = new SerialTest();
				      			Double progressValue = 0.1;
				      		    bershmlug.setProgress(progressValue);
				      			while (true) {
				      				if(st.testCom()){
				      					return true;
				      				}else{
				      					if(progressValue<0.9){
				      						progressValue+=0.1;
				      					}
				      					else{
				      						progressValue=0.1;
				      					}
				      					System.out.println("Cihaz algilanamadi");
				      				    bershmlug.setProgress(progressValue);
				      					Thread.sleep(500);
				      				}
				      			}	
				      		};

				      		executorService= Executors.newCachedThreadPool();
				      		executorService.submit(ro);
				      		
				  	        primaryStage.initStyle(StageStyle.UNDECORATED);
				  	        primaryStage.setTitle("Hello World");
				  	        Scene scene = new Scene(root);
				  	        primaryStage.setScene(scene);
				  	        primaryStage.show();
				  	       
				           } catch (IOException exception) {
				               throw new RuntimeException(exception);
				           }
				          
	
	      
	    }

	
	public static void main(String[] args) {
		
			
		launch(args);
	}
}
