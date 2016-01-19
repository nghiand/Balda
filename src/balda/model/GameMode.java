package balda.model;

/**
 *
 * @author Ngo Nghia
 */
public class GameMode {
    private enum Mode {TWO_PLAYERS, VS_COMPUTER};
    private Mode _mode;
    private String _computerName;
    
    public GameMode(){
        _mode = Mode.TWO_PLAYERS;
    }
    
    public GameMode(String computerName){
        _mode = Mode.VS_COMPUTER;
        _computerName = computerName;
    }
    
    public boolean isTwoPlayers(){
        return _mode == Mode.TWO_PLAYERS;
    }
    
    public String computerName(){
        return _computerName;
    }
}
