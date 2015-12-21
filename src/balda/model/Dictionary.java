package balda.model;

import java.util.ArrayList;

public class Dictionary {
    protected ArrayList _dictionary = new ArrayList<String>();
    protected boolean _modified;
    
    public void addWord(String word){
        int pos = 0;
        for (int i = 0; i < _dictionary.size(); i++){
            if (((String)_dictionary.get(i)).compareTo(word) >= 0){
                pos = i;
                break;
            }
        }
        _dictionary.add(pos, word);
    }
    
    public boolean isInDictionary(String word){
        for (Object obj : _dictionary){
            if (((String) obj).compareTo(word) == 0) return true;
        }
        return false;
    }
}
