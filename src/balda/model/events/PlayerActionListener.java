/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package balda.model.events;

import java.util.EventListener;

/**
 *
 * @author Ngo Nghia
 */
public interface PlayerActionListener extends EventListener{
    void letterIsPlaced(PlayerActionEvent e);
    void letterIsAppended(PlayerActionEvent e);
    void freeCellIsChoosen(PlayerActionEvent e);
    void wordNotContainsCell(PlayerActionEvent e);
    void usedWord(PlayerActionEvent e);
    void nonexistentWord(PlayerActionEvent e);
    void scored(PlayerActionEvent e);
}
