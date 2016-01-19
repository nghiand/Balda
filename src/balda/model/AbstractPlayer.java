package balda.model;

import balda.model.events.PlayerActionEvent;
import balda.model.events.PlayerActionListener;
import java.awt.Point;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Abstract class for description players
 * 
 */
public abstract class AbstractPlayer {
    private int _score = 0;
    protected boolean _addingLetter = true;
    protected GameField _field;
    protected Word _currentWord = new Word();
    protected Cell _currentCell;
    protected String _name;
    protected Dictionary _database;
    protected UsedWords _used;
    
    /**
     * Constructor
     * @param name Player's name
     * @param field Gamefield
     * @param database Dictionary dictionary
     * @param used Common dictionary, which stores used words
     */
    public AbstractPlayer(String name, GameField field, 
            Dictionary database, UsedWords used){
        _name = name;
        _field = field;
        _database = database;
        _used = used;
    }
    
    /**
     * Get player's score
     * @return score
     */
    public int getScore(){
        return _score;
    }
    
    /**
     * Add player's score
     * @param score score
     */
    public void addScore(int score){
        _score += score;
    }
    
    /**
     * Get player's name
     * @return players name
     */
    public String name(){
        return _name;
    }
    
    /**
     * Is player adding letter
     * @return true if player is adding letter
     */
    public boolean isAddingLetter(){
        return _addingLetter;
    }
    
    /**
     * Set adding letter flag
     * @param flag is adding letter
     */
    public void setAddingLetter(boolean flag){
        _addingLetter = flag;
    }
    
    /**
     * Set last cell, which was chosen by player while specifying word
     * @param c last cell
     */
    public void setCurrentCell(Cell c){
        _currentCell = c;
    }
    
    /**
     * Get last cell, which was chosen by player while specifying word
     * @return last cell
     */
    public Cell currentCell(){
        return _currentCell;
    }
    
    /**
     * Get chosen word
     * @return chosen word
     */
    public Word currentWord(){
        return _currentWord;
    }
    
    /**
     * Set letter to chosen cell
     * @param letter letter
     */
    public void setLetter(char letter){
        if (_currentCell != null){
            _currentCell.setLetter(letter);
            setAddingLetter(false);
            _currentWord.clear();
            fireLetterIsPlaced(_currentCell);
        }
    }
    
    /**
     * Append letter, which is in position pos, to chosen word
     * @param pos position
     */
    public void appendLetter(Point pos){
        if (!_addingLetter){
            Cell currentCell = _field.cell(pos);
            if (currentCell.isAvailable()) return;
            if (currentWord().contains(currentCell)) return;
            if (!currentWord().isAdjacent(currentCell)) return;
            
            _currentWord.addLetter(currentCell);
            fireLetterIsAppended(currentCell);
        }
    }
    
    /**
     * Choose cell in position pos
     * @param pos position
     */
    public void chooseCell(Point pos){
        if (_addingLetter && _field.isAvailable(pos)){
            _currentCell = _field.cell(pos);
            fireFreeCellIsChoosen(_currentCell);            
        }
    }
    
    /**
     * Submit chosen word to get score
     */
    public void submitWord(){
        String word = _currentWord.word();
        if (!_currentWord.contains(currentCell()))
            fireWordNotContainsCell();
        else
        if (_used.isInDictionary(word))
            fireUsedWord();
        else
        if (!_database.isInDictionary(word))
            fireNonexistentWord();
        else{
            _used.addWord(word, this);

            int score = word.length();
            addScore(score);
            fireWordIsSubmitted();
        }        
    }
    
    /**
     * Skip turn
     */
    public void skipTurn(){
        _addingLetter = true;
        fireSkipedTurn();
    }
    
    /**
     * Player click on a cell
     * @param pos position of cell
     */    
    public void cellIsActivated(Point pos){
        if (this.isAddingLetter()){
            this.chooseCell(pos);
        } else{
            this.appendLetter(pos);
        }        
    }
    
    /**
     * Player added word to database dictionary
     */
    public void addWordToDictionary(){
        String word = this.currentWord().word();
        _database.addWord(word);
    }
    
    /**
     * Clear chosen word and cell after submitting word
     */
    public void clear(){
        _currentWord.clear();
        _currentCell = null;
        _addingLetter = true;
    }
    
    // listener
    
    private ArrayList _playerListenerList = new ArrayList();
    
    public void addPlayerActionListener(PlayerActionListener l){
        _playerListenerList.add(l);
    }
    
    private final Logger fLogger=Logger.getLogger(this.getClass().getPackage().getName());
    
    protected void fireLetterIsPlaced(Cell c){
        fLogger.info("AbstractPlayer: Letter " + c.letter() + " is placed on Cell " + c.position());
        for (Object listener : _playerListenerList){
            PlayerActionEvent e = new PlayerActionEvent(this);
            e.setCell(c);
            e.setPlayer(this);
            ((PlayerActionListener)listener).letterIsPlaced(e);
        }
    }
    
    protected void fireFreeCellIsChoosen(Cell c){
        fLogger.info("AbstractPlayer: Free cell " + c.position() + "is choosen");
        for (Object listener : _playerListenerList){
            PlayerActionEvent e = new PlayerActionEvent(this);
            e.setCell(c);
            e.setPlayer(this);
            ((PlayerActionListener)listener).freeCellIsChoosen(e);
        }        
    }
    
    protected void fireLetterIsAppended(Cell c){
        fLogger.info("AbstractPlayer: Letter " + c.letter() + "is appended to choosen word");
        for (Object listener : _playerListenerList){
            PlayerActionEvent e = new PlayerActionEvent(this);
            e.setCell(c);
            e.setPlayer(this);
            ((PlayerActionListener)listener).letterIsAppended(e);
        }            
    }
    
    protected void fireWordNotContainsCell(){
        fLogger.info("AbstractPlayer: Player submitted word, which doesnt containt choosen word");
        for (Object listener : _playerListenerList){
            PlayerActionEvent e = new PlayerActionEvent(this);
            e.setPlayer(this);
            ((PlayerActionListener)listener).wordNotContainsCell(e);
        }                
    }
    
    protected void fireUsedWord(){
        fLogger.info("AbstractPlayer: Player submitted used-word");
        for (Object listener : _playerListenerList){
            PlayerActionEvent e = new PlayerActionEvent(this);
            e.setPlayer(this);
            ((PlayerActionListener)listener).usedWord(e);
        }        
    }
    
    protected void fireNonexistentWord(){
        fLogger.info("AbstractPlayer: Player submitted nonexistent word");
        for (Object listener : _playerListenerList){
            PlayerActionEvent e = new PlayerActionEvent(this);
            e.setPlayer(this);
            ((PlayerActionListener)listener).nonexistentWord(e);
        }                
    }
    
    protected void fireWordIsSubmitted(){
        fLogger.info("AbstractPlayer: Player submitted valid word");
        for (Object listener : _playerListenerList){
            PlayerActionEvent e = new PlayerActionEvent(this);
            e.setPlayer(this);
            ((PlayerActionListener)listener).wordIsSubmitted(e);
        }                        
    }
    
    protected void fireSkipedTurn(){
        fLogger.info("AbstractPlayer: Player skiped turn");
        for (Object listener : _playerListenerList){
            PlayerActionEvent e = new PlayerActionEvent(this);
            e.setPlayer(this);
            ((PlayerActionListener)listener).skipedTurn(e);
        }                        
    }    
}
