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
public class AiEasyMode extends AiMode{
    public static final String _name = "Easy";
    private static final int _difficulty = 3;
    
    public AiEasyMode(){
        super(_name, _difficulty);
    }
}
