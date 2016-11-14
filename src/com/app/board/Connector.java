package com.app.board;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import com.sun.media.jfxmedia.events.PlayerTimeListener;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;



public class Connector extends Task<Void> implements SerialPortEventListener{
	 
    private SerialPort serialPort;
    private static final String PORT_NAMES[] = {
    		 "COM1", "COM2","COM3","COM4","COM5","COM6","COM7","COM8","COM9","COM10","COM11","COM12","COM13"          
    };
    private BufferedReader input;
    private OutputStream output;
    private static final int TIME_OUT = 2000;
    private static final int DATA_RATE = 9600;
    private CommPortIdentifier portId = null;
    private Enumeration<CommPortIdentifier> portEnum;
    public List<Integer> list;
    private LineChart lineChart;
    private Integer point;
    private  Series series= new XYChart.Series();
    private ProgressBar progressBar;
    private Label progressLabel;
    private double start,end,speed,sens;
    private String inputLine;
    private double oldVal,newVal;
    public Connector(LineChart lc,ProgressBar pb,Label progressLabel,int start,int end,int speed,int sens){
        this.lineChart = lc;
    	list = new ArrayList<Integer>();
    	point = new Integer(0);
    	this.start= start;
    	this.end = end;
    	this.speed = speed;
    	this.sens = sens;
    	this.progressBar= pb;
    	this.progressLabel = progressLabel;
    
    }
  
    public void readerSetup() {
    			try {
            serialPort = (SerialPort) portId.open(this.getClass().getName(),
                    TIME_OUT);
            serialPort.setSerialPortParams(DATA_RATE,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);

            // open the streams
            input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
            output = serialPort.getOutputStream();

            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }
    public boolean searchCom(){
    	this.portEnum= CommPortIdentifier.getPortIdentifiers();
  
        while (portEnum.hasMoreElements()) {
            CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
            for (String portName : PORT_NAMES) {
            	System.out.print("Testing Adress = "+portName);
                if (currPortId.getName().equals(portName)) {
                    portId = currPortId;
//                    System.out.println(" found");
                    break;
                }else{
//                	 System.out.println(" not found");
                }
            }
        }
        if (portId == null) {
            System.out.println("Could not find COM port.");
            return false;
        }else{
            System.out.println("\n Founded on = "+portId.getName()+"\n");
        	return true;
        }
    } 
    public synchronized void close() {
        if (serialPort != null) {
            serialPort.removeEventListener();
            serialPort.close();
        }
    }
    
    
    
    public Runnable a = ()-> {
    	     
    	     newVal = Double.valueOf(inputLine);
    		
    	    if(newVal != oldVal){ 
    	     double d =1-(((end-start)/speed)-point)/(end-start);
    	     series.getData().add((new XYChart.Data((point).toString(),Double.valueOf(inputLine))));      
    	     if(series.getData().size() ==1) lineChart.getData().add(series);  	       
    	     progressLabel.setText("%"+new Double(d*100).intValue());
    	     progressBar.setProgress(d);
    	     oldVal = newVal;
    	    }
    	
    };

    public synchronized void serialEvent(SerialPortEvent oEvent) {
        if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE && !this.isCancelled() && (end-start-point>0)) {
            try {
                if (input.ready()) {
                	point = point+1;
                	double d =1-(((end-start)/speed)-point)/(end-start);
                    inputLine = input.readLine();
                    System.out.println(inputLine);
//         	        series.getData().add((new XYChart.Data((point).toString(),Double.valueOf(inputLine))));      
         	        Platform.runLater(a);
                }

            } catch (Exception e) {
                System.err.println(e.toString());
            }
        }
    }

    public void write(int number){
    	try {
			this.output.write(number);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	@Override
	protected Void call() throws Exception {
		searchCom();
		readerSetup();
		
		return null;
	}
    
}