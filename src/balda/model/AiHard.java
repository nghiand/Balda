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
public class AiHard extends AiMode {
    private static final String _name = "Hard";
    private static final int _difficulty = 6;
    
    public AiHard(){
        super(_name, _difficulty);
    }    
}
