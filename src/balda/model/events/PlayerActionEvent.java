package balda.model.events;

import balda.model.AbstractPlayer;
import balda.model.Cell;
import java.util.EventObject;

public class PlayerActionEvent extends EventObject {
    public PlayerActionEvent(Object source){
        super(source);
    }
    
    AbstractPlayer _player;
    public void setPlayer(AbstractPlayer player){
        _player = player;
    }
    
    public AbstractPlayer player(){
        return _player;
    }
    
    Cell _cell;
    public void setCell(Cell cell){
        _cell = cell;
    }
    
    public Cell cell(){
        return _cell;
    }
}
