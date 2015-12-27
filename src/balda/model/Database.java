package balda.model;

import java.io.*;
import java.util.ArrayList;

/**
 * Database dictionary
 */
public class Database{
    private ArrayList _dictionary = new ArrayList<String>();
    private String _filename = "dictionary/dictionary.txt";
    private boolean _modified;
    
    /**
     * Constructor
     */
    public Database(){
        readFromFile();
        _modified = false;
    }
    
    /**
     * Add new word to dictionary
     * @param word Word
     */
    public void addWord(String word){
        int pos = 0;
        for (int i = 0; i < _dictionary.size(); i++){
            if (((String)_dictionary.get(i)).compareTo(word) >= 0){
                pos = i;
                break;
            }
        }
        _dictionary.add(pos, word);  
        _modified = true;
    }
    
    /**
     * Check if a word is in database dictionary
     * @param word Word
     * @return true if word is in database
     */
    public boolean isInDictionary(String word){
        for (Object obj : _dictionary){
            if (((String) obj).compareTo(word) == 0) return true;
        }
        return false;
    }    
    
    /**
     * Read database from file when game starts
     */
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
    
    /**
     * Save database when there was any change
     */
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
    
    /**
     * Get word with position in database
     * @param pos Position
     * @return Word
     */
    String getWord(int pos){
        pos = pos % _dictionary.size(); 
        return (String) _dictionary.get(pos);
    }
}
