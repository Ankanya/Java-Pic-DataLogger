package com.app.save;

import java.io.Serializable;
import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;

public class ChartData implements Serializable {

	public static class Datar implements Serializable {
		String x;
		Integer y;
	
		public Datar(Object xValue, Object yValue) {
			this.x = x;
			this.y=y;
		}
		
	}
	

	private static final long serialVersionUID = 1L;
    private ArrayList<Datar> arrayList;
    
    public ChartData(Series<String, Integer> series){
    	XSSFWorkbook workbook = new XSSFWorkbook();

        arrayList = new ArrayList<>();
    	for(int i =0;i<series.getData().size();i++){
    		XYChart.Data  data = series.getData().get(i);
    		arrayList.add(new Datar(data.getXValue(),data.getYValue()));
    		
    	}
    	
     
    }
   
}
