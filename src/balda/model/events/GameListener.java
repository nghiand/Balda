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
}
