package balda.model;

/**
 *
 * @author Ngo Nghia
 */
public class GameMode {
    private enum Mode {TWO_PLAYERS, VS_COMPUTER};
    private Mode _mode;
    private String _computerName;
    
    GameMode(){
        _mode = Mode.TWO_PLAYERS;
    }
    
    GameMode(String computerName){
        _mode = Mode.VS_COMPUTER;
        _computerName = computerName;
    }
    
    boolean isTwoPlayers(){
        return _mode == Mode.TWO_PLAYERS;
    }
    
    String computerName(){
        return _computerName;
    }
}
