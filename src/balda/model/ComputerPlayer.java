/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package balda.model;

/**
 *
 * @author Ngo Nghia
 */
public class ComputerPlayer extends AbstractPlayer{
    private int _difficulty;
    
    public ComputerPlayer(int difficulty, GameField field, 
            Database database, UsedDictionary used){
        super("Computer", field, database, used);
        _difficulty = difficulty;
    }
    
    public void move(){
    }
}
