package com.app.loading;
	
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.app.board.Connector;
import com.app.gui.GuiApplication;

import application.SerialTest;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;


public class LoadingApplication extends Application {
	private static ExecutorService executorService;
	private static String[] args;
	private Stage primaryStage;
	@FXML
	public ProgressBar d1z1;
	
	
	public static void main(String args[]){
		LoadingApplication la = new LoadingApplication();
		la.launch(args);
	}
	
 
    		//Responsible for gettin fxml file and building screen
    private void createScreen(Stage primaryStage){
    	      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("loading.fxml"));
			  fxmlLoader.setController(this);
			  try{
				  Parent root =  fxmlLoader.load();	
			      primaryStage.initStyle(StageStyle.UNDECORATED);
			      Scene scene = new Scene(root);
			      scene.getStylesheets().add("com/app/loading/loading.css");
		  	      primaryStage.setScene(scene);		 
		  	      this.primaryStage= primaryStage;
		  	      primaryStage.show();
				

		  	      Task<Void> gorev = new Task<Void>(){

					@Override
					protected Void call() throws Exception {
						double i = 0.01;
						while(i<1){
							i=i+0.01;
							d1z1.setProgress(i);
							 Thread.sleep(100);
							 SerialTest st = new SerialTest();
							 st.testCom();
						
						}					
						
						
						return null;
					}
		  	    	  
		  	      };
		  	      
		  	      new Thread(gorev).start();
		  	      
			  }catch(IOException exception){
				  System.out.println(exception);
			  }
    }
    
  
    
	public void start(Stage primaryStage) throws Exception{
		  
		  createScreen(primaryStage);
				          
	}
	
			//This method calls createScreen(); with primaryStage;
	public static void startApplication(String[] args) {
		executorService = Executors.newCachedThreadPool();
		LoadingApplication.args=  args;
		launch(args);
	}
}
	
	

