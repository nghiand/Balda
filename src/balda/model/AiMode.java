/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package balda.model;

/**
 * Abstract class for representation modes of artificial intelligence
 */
public abstract class AiMode {
    protected int _difficulty;
    public static String _name;
    
    /**
     * Constructor
     * @param name name of Ai mode
     * @param difficulty difficulty of Ai mode
     */
    public AiMode(String name, int difficulty){
        _name = name;
        _difficulty = difficulty;
    }
    
    public int difficulty(){
        return _difficulty;
    }
    
    public static String name(){
        return _name;
    }
}
