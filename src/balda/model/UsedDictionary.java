package balda.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UsedDictionary {
    private Map<String, AbstractPlayer> _dictionary = new HashMap();
    
    public void addWord(String word, AbstractPlayer player){
        _dictionary.put(word, player);
    }
    
    public boolean isInDictionary(String word){
        for (Map.Entry<String, AbstractPlayer> entry : _dictionary.entrySet()){
            if (((String) entry.getKey()).compareTo(word) == 0) return true;
        }
        return false;
    }
}
