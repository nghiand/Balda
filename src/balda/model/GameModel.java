/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package balda.model;
import balda.model.GameMode;
import balda.model.events.GameEvent;
import balda.model.events.GameListener;
import balda.model.events.PlayerActionEvent;
import balda.model.events.PlayerActionListener;
import java.awt.Point;
import java.util.ArrayList;
import java.util.logging.Logger;
/**
 *
 * @author Ngo Nghia
 */
public class GameModel {
    
    private GameField _field = new GameField();
    private AbstractPlayer _activePlayer;
    private AbstractPlayer _otherPlayer;
    private GameMode _gameMode;
    private Database _database = new Database();
    private UsedDictionary _used = new UsedDictionary();
    
    public GameModel(int width, int height, GameMode gameMode){
        generateField(width, height);
        _gameMode = gameMode;
        
        _otherPlayer = new Player("Player 1", _field, _database, _used);
        _otherPlayer.addPlayerActionListener(new PlayerObserve());
        if (_gameMode == GameMode.TWO_PLAYERS){
            _activePlayer = new Player("Player 2", _field, _database, _used);
        } else{
            if (_gameMode == GameMode.EASY)
                _activePlayer = new ComputerPlayer(3, _field, _database, _used);
            else if (_gameMode == GameMode.NORMAL)
                _activePlayer = new ComputerPlayer(4, _field, _database, _used);
            else _activePlayer = new ComputerPlayer(5, _field, _database, _used);
        }
        _activePlayer.addPlayerActionListener(new PlayerObserve());
    }
    
    public GameField field(){
        return _field;
    }
    
    public void start(){
        String startingWord = "ABC";
        
        int l = startingWord.length();
        int row = (field().height() + 1) / 2;
        int col = (field().width() - l) / 2 + 1;
        for (int i = 0; i < l; i++){
            Cell c = field().cell(new Point(row, col + i));
            c.setLetter(startingWord.charAt(i));
        }
        
        fireStartingWordIsChoosen(startingWord);
        exchangePlayer();
    }
    
    public void exchangePlayer(){
        AbstractPlayer temp = _activePlayer;
        _activePlayer = _otherPlayer;
        _otherPlayer = temp;
        _activePlayer.clear();
        firePlayerExchanged(_activePlayer);
    }
    
    private void generateField(int width, int height){
        field().clear();
        field().setSize(width, height);
        for (int row = 1; row <= field().height(); row++){
            for (int col = 1; col <= field().width(); col++){
                field().setCell(new Point(row, col), new Cell());
            }
        }
    }
    
    public AbstractPlayer activePlayer(){
        return _activePlayer;
    }
    
    public void clickOnCell(Point pos){
        if (_activePlayer.isAddingLetter()){
            _activePlayer.addLetter(pos);
        } else{
            _activePlayer.appendLetter(pos);
        }
    }
    
    public void submittedWord(){
        String winner = determineWinner();
        if (winner != null){
            fireGameFinished(winner);
        }
        exchangePlayer();        
    }
    
    private String determineWinner(){
        if (field().isFull()){
            if (_activePlayer.getScore() > _otherPlayer.getScore())
                return _activePlayer.name();
            if (_activePlayer.getScore() < _otherPlayer.getScore())
                return _otherPlayer.name();
            return "Draw";
        }
        return null;
    }
    
    //----------------------------------------------------------
    
    private class PlayerObserve implements PlayerActionListener{
        @Override
        public void letterIsPlaced(PlayerActionEvent e){
            if (e.player() == activePlayer()){
                fireLetterIsPlaced(e);
            }
        }
        
        @Override
        public void freeCellIsChoosen(PlayerActionEvent e){
            if (e.player() == activePlayer()){
                fireFreeCellIsChoosen(e);
            }
        }
        
        @Override
        public void letterIsAppended(PlayerActionEvent e){
            if (e.player() == activePlayer()){
                fireLetterIsAppended(e);
            }
        }

        @Override
        public void wordNotContainsCell(PlayerActionEvent e) {
            if (e.player() == activePlayer()){
                fireWordNotContainsCell(e);
            }
        }

        @Override
        public void usedWord(PlayerActionEvent e) {
            if (e.player() == activePlayer()){
                fireUsedWord(e);
            }
        }

        @Override
        public void nonexistentWord(PlayerActionEvent e) {
            if (e.player() == activePlayer()){
                fireNonexistentWord(e);
            }            
        }

        @Override
        public void wordIsSubmitted(PlayerActionEvent e) {
            if (e.player() == activePlayer()){
                fireWordIsSubmitted(e);
                submittedWord();
            }
            
        }
    }
    
    //
    private ArrayList _playerListenerList = new ArrayList();
    
    public void addPlayerActionListener(PlayerActionListener l){
        _playerListenerList.add(l);
    }
    
    private final Logger fLogger=Logger.getLogger(this.getClass().getPackage().getName());
     
    protected void fireLetterIsPlaced(PlayerActionEvent e){
        fLogger.info("GameModel: Letter " + e.cell().letter() + " is placed");
        for (Object listener : _playerListenerList){
            ((PlayerActionListener) listener).letterIsPlaced(e);
        }
    }
    
    protected void fireLetterIsAppended(PlayerActionEvent e){
        fLogger.info("GameModel: Letter " + e.cell().letter() + " is appended");
        for (Object listener : _playerListenerList){
            ((PlayerActionListener) listener).letterIsAppended(e);
        }
    }
    
    protected void fireFreeCellIsChoosen(PlayerActionEvent e){
        fLogger.info("GameModel: Cell " + e.cell().position() + " is choosen");
        for (Object listener : _playerListenerList){
            ((PlayerActionListener) listener).freeCellIsChoosen(e);
        }
    }
    
    protected void fireWordNotContainsCell(PlayerActionEvent e){
        fLogger.info("GameModel: Player submitted word, which doesnt containt choosen word");    
        for (Object listener : _playerListenerList){
            ((PlayerActionListener) listener).wordNotContainsCell(e);
        }        
    }    

    protected void fireUsedWord(PlayerActionEvent e){
        fLogger.info("GameModel: Player submitted used-word");
        for (Object listener : _playerListenerList){
            ((PlayerActionListener) listener).usedWord(e);
        }        
    }    

    protected void fireNonexistentWord(PlayerActionEvent e){
        fLogger.info("GameModel: Player submitted nonexistent word");
        for (Object listener : _playerListenerList){
            ((PlayerActionListener) listener).nonexistentWord(e);
        }        
    }
    
    protected void fireWordIsSubmitted(PlayerActionEvent e){
        fLogger.info("GameModel: Player submitted valid word");
        for (Object listener : _playerListenerList){
            ((PlayerActionListener) listener).wordIsSubmitted(e);
        }        
    }
    
    
    
    // game listeners ----------------------------------
    private ArrayList _listenerList = new ArrayList();
    
    public void addGameListener(GameListener l){
        _listenerList.add(l);
    }
    
    protected void fireGameFinished(String word){
        fLogger.info("GameModel: Game finished");
        GameEvent e = new GameEvent(this);
        e.setWord(word);
        for (Object listener : _listenerList){
            ((GameListener) listener).gameFinished(e);
        }        
    }
    
    protected void fireStartingWordIsChoosen(String word){
        fLogger.info("GameModel: Starting word was choosen");        
        GameEvent e = new GameEvent(this);
        e.setWord(word);
        for (Object listener : _listenerList){
            ((GameListener) listener).startingWordIsChoosen(e);
        }
    }
    
    protected void firePlayerExchanged(AbstractPlayer player){
        fLogger.info("GameModel: Player Exchanged");
        GameEvent e = new GameEvent(this);
        e.setPlayer(player);
        for (Object listener : _listenerList){
            ((GameListener) listener).playerExchanged(e);
        }
    }    
}
