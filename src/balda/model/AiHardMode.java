/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package balda.model;

/**
 * Class, which is representation for Ai Hard mode
 */
public class AiHardMode extends AiMode {
    public static final String _name = "Hard";
    private static final int _difficulty = 6;
    
    public AiHardMode(){
        super(_name, _difficulty);
    }    
}
