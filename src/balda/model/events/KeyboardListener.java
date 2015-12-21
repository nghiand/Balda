package balda.model.events;

import java.util.EventListener;

public interface KeyboardListener extends EventListener{
    public void keyIsPressed(KeyboardEvent e);
}
