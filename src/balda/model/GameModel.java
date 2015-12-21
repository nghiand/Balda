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
    private DatabaseDictionary _dbDictionary;
    
    public GameModel(int width, int height, GameMode gameMode){
        generateField(width, height);
        _gameMode = gameMode;
        
        _otherPlayer = new Player("Player 1", _field);
        _otherPlayer.addPlayerActionListener(new PlayerObserve());
        if (_gameMode == GameMode.TWO_PLAYERS){
            _activePlayer = new Player("Player 2", _field);
        } else{
            if (_gameMode == GameMode.EASY)
                _activePlayer = new ComputerPlayer(3, _field);
            else if (_gameMode == GameMode.NORMAL)
                _activePlayer = new ComputerPlayer(4, _field);
            else _activePlayer = new ComputerPlayer(5, _field);
        }
        _activePlayer.addPlayerActionListener(new PlayerObserve());
    }
    
    public GameField field(){
        return _field;
    }
    
    public void start(){
        _dbDictionary = new DatabaseDictionary();
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
    
    public void determineScore(){
        String word = activePlayer().currentWord().word();
        if (!_activePlayer.currentWord().contains(_activePlayer.currentCell()))
            fireWordNotContainsCell(_activePlayer);
        else
        if (_activePlayer.dictionary().isInDictionary(word) || _otherPlayer.dictionary().isInDictionary(word))
            fireUsedWord(_activePlayer);
        else
        if (!_dbDictionary.isInDictionary(word))
            fireNonexistentWord(_activePlayer);
        else{
            _activePlayer.dictionary().addWord(word);

            int score = word.length();
            _activePlayer.addScore(score);
            firePlayerScored(_activePlayer);
            
            String winner = determineWinner();
            if (winner != null){
                fireGameFinished(winner);
            }
            exchangePlayer();
        }
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
    
    protected void firePlayerScored(AbstractPlayer player){
        fLogger.info("GameModel: Player " + player.name() + " scored");                
        GameEvent e = new GameEvent(this);
        e.setPlayer(player);
        for (Object listener : _listenerList){
            ((GameListener) listener).playerScored(e);
        }        
    }
    
    protected void fireNonexistentWord(AbstractPlayer player){
        fLogger.info("GameModel: Player submited nonexistent word");                
        GameEvent e = new GameEvent(this);
        e.setPlayer(player);
        for (Object listener : _listenerList){
            ((GameListener) listener).nonexistentWord(e);
        }         
    }
    
    protected void fireUsedWord(AbstractPlayer player){
        fLogger.info("GameModel: Player submitted used word");                
        GameEvent e = new GameEvent(this);
        e.setPlayer(player);
        for (Object listener : _listenerList){
            ((GameListener) listener).usedWord(e);
        }        
    }
    
    protected void fireWordNotContainsCell(AbstractPlayer player){
        fLogger.info("GameModel: Player submitted word, which doesn\'t contain choosen cell");                
        GameEvent e = new GameEvent(this);
        e.setPlayer(player);
        for (Object listener : _listenerList){
            ((GameListener) listener).wordNotContainsCell(e);
        }        
    }   
}
