/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package balda.model;

import java.io.*;

/**
 *
 * @author Ngo Nghia
 */
public class DatabaseDictionary extends Dictionary{
    
    private String _filename = "dictionary/dictionary.txt";
    
    public DatabaseDictionary(){
        readFromFile();
    }
    
    private void readFromFile(){
        try{
            FileReader fileReader = new FileReader(_filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null){
                _dictionary.add(line);
                //System.out.println(line);
            }
            bufferedReader.close();
        }
        catch (FileNotFoundException ex) {
            System.out.println("Unable to open file");                
        }
        catch(IOException ex) {
            System.out.println("Error reading file");                  
            // ex.printStackTrace();
        }           
    }
    
    public void writeToFile(){
        if (_modified){
            try {
                FileWriter fileWriter = new FileWriter(_filename);

                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                for (Object c : _dictionary){
                    bufferedWriter.write((String)c);
                    bufferedWriter.newLine();
                }
                bufferedWriter.close();
            }
            catch(IOException ex) {
                System.out.println("Error writing to file");
                // ex.printStackTrace();
            }
        }
    }
}
