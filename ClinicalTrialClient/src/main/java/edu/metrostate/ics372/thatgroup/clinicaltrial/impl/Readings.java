package edu.metrostate.ics372.thatgroup.clinicaltrial.impl;
import java.io.*;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.*;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;



public class Readings { 
	{

try {
	File myFile = new File("JSON.txt");
    FileInputStream inputFile = new FileInputStream(myFile);
    BufferedReader myReader = new BufferedReader(new InputStreamReader(inputFile));
    String aDataRow = "";
    
    String aBuffer = "" ; //Holds the text
    while ((aDataRow = myReader.readLine()) != null) 
    {
        aBuffer += aDataRow ;
    }
    myReader.close();
    
	} catch (FileNotFoundException e ) {
				e.printStackTrace();
	}catch (IOException ex) {
				ex.printStackTrace();
	}catch (Exception en) {
				en.getMessage();
				
			}
	}
}
	
			
