package balda.model.events;

import java.util.EventListener;

/**
 *
 * @author Ngo Nghia
 */
public interface GameListener extends EventListener {
    public void gameFinished(GameEvent e);
    public void playerExchanged(GameEvent e);
    public void startingWordIsChoosen(GameEvent e);
    public void playerScored(GameEvent e);
    public void usedWord(GameEvent e);
    public void nonexistentWord(GameEvent e);   
    public void wordNotContainsCell(GameEvent e);
}
