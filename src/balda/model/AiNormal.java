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
public class AiNormal extends AiMode {
    private static final String _name = "Normal";
    private static final int _difficulty = 4;
    
    public AiNormal(){
        super(_name, _difficulty);
    }    
}
