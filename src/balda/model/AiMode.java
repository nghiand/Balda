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
public abstract class AiMode {
    protected int _difficulty;
    protected String _name;
    
    public AiMode(String name, int difficulty){
        _name = name;
        _difficulty = difficulty;
    }
}