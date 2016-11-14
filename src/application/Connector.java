package application;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;




public class Connector implements SerialPortEventListener,Runnable {
	
    private SerialPort serialPort;
    private static final String PORT_NAMES[] = {
    		 "COM1", "COM2","COM3","COM4","COM5","COM6","COM7","COM8","COM9","COM10","COM11","COM12","COM13"          
    };
    private BufferedReader input;
    private OutputStream output;
    private static final int TIME_OUT = 2000;
    private static final int DATA_RATE = 9600;
    private CommPortIdentifier portId = null;
    private Enumeration portEnum;
    
    public Connector(){
    	
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

    public synchronized void serialEvent(SerialPortEvent oEvent) {
        if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            try {
                String inputLine=null;
                if (input.ready()) {
                    inputLine = input.readLine();
                    System.out.println(inputLine);
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
	public void run() {
	    if(searchCom()){
	    	readerSetup();
	    }
	}
}