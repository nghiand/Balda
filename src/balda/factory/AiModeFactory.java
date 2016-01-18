/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package balda.factory;

import balda.model.AiEasyMode;
import balda.model.AiHardMode;
import balda.model.AiNormalMode;

/**
 *
 * @author Ngo Nghia
 */
public class AiModeFactory {
    public AiEasyMode createAiEasyMode(){
        return new AiEasyMode();
    }
    
    public AiNormalMode createAiNormalMode(){
        return new AiNormalMode();
    }
    
    public AiHardMode createAiHardMode(){
        return new AiHardMode();
    }
}
