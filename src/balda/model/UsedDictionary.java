package balda.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Common dictionary, in which used words
 */
public class UsedDictionary {
    private Map<String, AbstractPlayer> _dictionary;
    
    /**
     * Constructor
     */
    public UsedDictionary(){
        _dictionary = new HashMap();
    }
    
    /**
     * Add a word to dictionary
     * @param word word
     * @param player player, who added word
     */
    public void addWord(String word, AbstractPlayer player){
        _dictionary.put(word, player);
    }
    
    /**
     * Check if a word is in dictionary
     * @param word word
     * @return true if word is in dictionary
     */
    public boolean isInDictionary(String word){
        for (Map.Entry<String, AbstractPlayer> entry : _dictionary.entrySet()){
            if (((String) entry.getKey()).compareTo(word) == 0) return true;
        }
        return false;
    }
}
