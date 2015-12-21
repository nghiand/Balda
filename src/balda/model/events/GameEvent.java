/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package balda.model.events;

import balda.model.AbstractPlayer;
import java.util.EventObject;
import balda.model.Player;

/**
 *
 * @author Ngo Nghia
 */
public class GameEvent extends EventObject{
    AbstractPlayer _player;
    String _word;
    
    public void setPlayer(AbstractPlayer player){
        _player = player;
    }
    
    public AbstractPlayer player(){
        return _player;
    }
    
    public void setWord(String word){
        _word = word;
    }
    
    public String word(){
        return _word;
    }
    
    public GameEvent(Object source){
        super(source);
    }
}
