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
public class Player extends AbstractPlayer {
    public Player(String name, GameField field, 
            Database database, UsedDictionary used){
        super(name, field, database, used);
    }
}
