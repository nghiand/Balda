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
public class AiHardMode extends AiMode {
    private static final String _name = "Hard";
    private static final int _difficulty = 6;
    
    public AiHardMode(){
        super(_name, _difficulty);
    }    
}
