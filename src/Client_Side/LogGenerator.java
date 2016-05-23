package Client_Side;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


public class LogGenerator {

    public static Writer writer = null;
    public static String dir = null;
	public static File f = null;
    
    public static void addLog(String msg){
        if(fileFlag){
            try {
                Date date = new Date();
                SimpleDateFormat ft =  new SimpleDateFormat ("dd/MM/yyyy hh:mm:ss a");
                writer.write("["+ft.format(date)+"] : "+ msg +"\n");
            } catch (IOException ex) {
                Logger.getLogger(LogGenerator.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            createLogFile();
            addLog(msg);
        }
    }
    
    public static void createLogFile(String log_type, String log_name){
        try {
            try {
            	switch(log_type){
            	case "c":
            		dir = "Client_Side_Log/";
            		f = new File(dir+log_name+".log");
            		if(!f.exists()){
            			try {
							f.createNewFile();
						} catch (IOException e) {
							e.printStackTrace();
						}
            		}
            		break;
            	case "s":
            		dir = "Server_Side_Log/";
            		f = new File(dir+log_name+".log");
            		if(!f.exists()){
            			try {
							f.createNewFile();
						} catch (IOException e) {
							e.printStackTrace();
						}
            		}
            		break;
            	}
                writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f,true), "utf-8"));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(LogGenerator.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(LogGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void closeLog(){
        try {
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(LogGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static String getLogTime(){
        Date date = new Date();
        SimpleDateFormat sdf =  new SimpleDateFormat ("yyyy/MM/dd hh:mm:ss a");
        return "["+sdf.format(date)+"] : ";
    }
    
    public static void main(String[] args) {
    	createLogFile("s","Server_MTL");
		System.out.println(getLogTime());
	}

}
