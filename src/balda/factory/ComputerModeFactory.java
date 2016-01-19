/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package balda.factory;

import balda.model.ComputerBruteForce;
import balda.model.ComputerPlayer;
import balda.model.ComputerRandomCell;
import balda.model.Dictionary;
import balda.model.GameField;
import balda.model.UsedWords;

/**
 *
 * @author Ngo Nghia
 */
public class ComputerModeFactory {
    public ComputerPlayer createAi(String computerName, GameField field, Dictionary database, UsedWords used){
        if (computerName == ComputerBruteForce.name){
            return new ComputerBruteForce(field, database, used);
        }
        if (computerName == ComputerRandomCell.name){
            return new ComputerRandomCell(field, database, used);            
        }
        throw new UnsupportedOperationException("Not supported yet.");        
    }
}
