package com.app.gui;

import java.io.IOException;


import com.app.board.Connector;
import com.app.save.Saver;

import application.SerialTest;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class GuiApplication extends Application {
	
	
	public void checkConenct(){
		Connector connector = new Connector(this.lineChart,this.progressBar,this.progressLabel,300,500,1,1);
		if(connector.searchCom()){
			statusLabel.setText("DEVICE CONNECTED");
			System.out.println("device connected");		
		}else{	
			statusLabel.setText("DEVICE IS NOT CONNECTED");
			System.out.println("device is not conencted");
		}	
	}

	@FXML
	public LineChart<String,Integer> lineChart;
	
	@FXML
	public Label statusLabel;

	@FXML
	public ImageView reflesh;
	
	public static void main(String args[]){
		GuiApplication.launch(null);
	}
	
	public void start(Stage primaryStage) throws Exception{
		  createScreen(primaryStage);
				          
	}
	
	@FXML
	public Label progressLabel;
	
	@FXML
	public ProgressBar progressBar;
	
	@FXML public Label pauseLabel;
	@FXML public ImageView pauseImageView;
	
	@FXML public Label startLabel;
	@FXML public ImageView startImageView;
	
	@FXML public TextField sensitivityTextField;
	@FXML public TextField startTextField;
	@FXML public TextField stopTextField;
	@FXML public TextField speedTextField;
	@FXML public Label xlsSave;
	
    private XYChart.Series<String,Integer> series;
    private  ObservableList<Series<String, Integer>> ege;
    
    private Thread connectorThread;
    private Connector connector;

    
    
    public void save(){
    	Saver.save(this.lineChart.getData().get(0));
    }
    
    private void  startConenctionThread(){
  	     connector = new Connector(this.lineChart,this.progressBar,this.progressLabel,
  	    		 Integer.valueOf(startTextField.getText()),
  	    		 Integer.valueOf(stopTextField.getText()),
  	    		 Integer.valueOf(speedTextField.getText()),
  	    		 Integer.valueOf(sensitivityTextField.getText()));
	      connectorThread = new Thread(connector);
	      connectorThread.start();
    }
	private void stopConenctionThread(){
		connector.close();
		
	}
    
	@FXML
    private void createScreen(Stage primaryStage){
	      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("gui.fxml"));
		  fxmlLoader.setController(this);
		  try{		  	      
			  Parent root =  fxmlLoader.load();	
		      Scene scene = new Scene(root);
		      scene.getStylesheets().add("com/app/gui/gui.css");
	  	      primaryStage.setScene(scene);		  	     
	  	      primaryStage.show();
	  	      Connector connector = new Connector(this.lineChart,this.progressBar,this.progressLabel,200,500,1,3);
	  	      
	  	      lineChart.setAnimated(false);
	  	      lineChart.set
	  	      

	  	     series= new XYChart.Series();

	        //defining a series
	        
//	        //populating the series with data
//	        series.getData().add(new XYChart.Data("1", 100));
//	        
//	        lineChart.getData().add(series);
	        series= new XYChart.Series();
//	        Task<Void> gorev = new Task<Void>(){
//				@Override
//				protected Void call() {
//					double i = 0.01;
//					try{     
//							 progressBar.setProgress(i);
//							 Thread.sleep(100);
//							 
//											
//					}catch(Exception e){
//						e.printStackTrace();
//					}	
//					return null;
//				}
//	  	    	  
//	  	      };
//	  	      

	      
	  	    reflesh.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
	  	      @Override
	  	      public void handle(MouseEvent mouseEvent) {
	  	    	checkConenct();
	  	      }
	  	      });
	  	    pauseImageView.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
		  	      @Override
		  	      public void handle(MouseEvent mouseEvent) {
		  	    	stopConenctionThread();
		  	      }
		  	      });
	  	    pauseLabel.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
		  	      @Override
		  	      public void handle(MouseEvent mouseEvent) {
		  	    	stopConenctionThread();
		  	      }
		  	      });
	  	    startLabel.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
		  	      @Override
		  	      public void handle(MouseEvent mouseEvent) {
		  	    	startConenctionThread();
		  	      }
		  	      });
	  	      
		    startImageView.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
		  	      @Override
		  	      public void handle(MouseEvent mouseEvent) {
		  	    	startConenctionThread();
		  	      }
		  	      });
		    xlsSave.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
		  	      @Override
		  	      public void handle(MouseEvent mouseEvent) {
		  	          
		  	    	  save();
		  
		  	      }
		  	 });
		    
		  }catch(IOException exception){
			  System.out.println(exception);
		  }
    }
}
