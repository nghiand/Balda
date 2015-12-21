package balda.model.events;

import java.util.EventObject;

public class KeyboardEvent extends EventObject{
    String _letter;
    
    public void setKey(String letter){
        _letter = letter;
    }
    
    public String getKey(){
        return _letter;
    }
    
    public KeyboardEvent(Object source){
        super(source);
    }
}
