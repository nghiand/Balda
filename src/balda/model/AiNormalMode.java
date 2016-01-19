/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package balda.model;

/**
 * Class, which is representation for Ai Normal mode
 */
public class AiNormalMode extends AiMode {
    public static final String _name = "Normal";
    private static final int _difficulty = 4;
    
    public AiNormalMode(){
        super(_name, _difficulty);
    }    
}
