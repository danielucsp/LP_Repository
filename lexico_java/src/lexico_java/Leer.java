/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexico_java;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Leer {
    
	BufferedReader reader;
	
    public List<String> readFile(String filename)
    {
        List<String> records = new ArrayList<String>();
        try
        {
          reader = new BufferedReader(new FileReader(filename));
          /*String line;
          while ((line = reader.readLine()) != null)
          {
            records.add(line);
          }
          reader.close();
          
              System.out.println(records);    */      
          return records;
        }
        catch (Exception e)
        {
          System.err.format("Exception occurred trying to read '%s'.", filename);
          e.printStackTrace();
          return null;
        }
     }  
    
}
