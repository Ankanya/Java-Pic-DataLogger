package com.app.save;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart.Series;

public class Saver {
	
	  public static void save(Series<String, Integer> series) {
		  ChartData cd = new ChartData(series);
	      try {
	         FileOutputStream fileOut=  new FileOutputStream("data");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(cd);
	         out.close();
	         fileOut.close();
	         System.out.printf("Serialized data is saved in /tmp/employee.ser");
	      }catch(IOException i) {
	         i.printStackTrace();
	      }
	   }
}
