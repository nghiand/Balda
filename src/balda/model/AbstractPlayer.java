/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package balda.model;

import balda.model.events.PlayerActionEvent;
import balda.model.events.PlayerActionListener;
import java.awt.Point;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 *
 * @author Ngo Nghia
 */
public abstract class AbstractPlayer {
    private int _score = 0;
    private UsedDictionary _dictionary = new UsedDictionary();
    private boolean _addingLetter = true;
    private GameField _field;
    private Word _currentWord = new Word();
    private Cell _currentCell;
    private String _name;
    private Database _database;
    private UsedDictionary _used;
    
    public AbstractPlayer(String name, GameField field, 
            Database database, UsedDictionary used){
        _name = name;
        _field = field;
        _database = database;
        _used = used;
    }
    
    public int getScore(){
        return _score;
    }
    
    public void addScore(int score){
        _score += score;
    }
    
    public String name(){
        return _name;
    }
    
    public boolean isAddingLetter(){
        return _addingLetter;
    }
    
    public void setAddingLetter(boolean flag){
        _addingLetter = flag;
    }
    
    public void setCurrentCell(Cell c){
        _currentCell = c;
    }
    
    public Cell currentCell(){
        return _currentCell;
    }
    
    public Word currentWord(){
        return _currentWord;
    }
    
    public void setLetter(char letter){
        _currentCell.setLetter(letter);
        setAddingLetter(false);
        _currentWord.clear();
        fireLetterIsPlaced(_currentCell);
    }
    
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
    
    public void addLetter(Point pos){
        if (_addingLetter && _field.isAvailable(pos)){
            _currentCell = _field.cell(pos);
            fireFreeCellIsChoosen(_currentCell);            
        }
    }
    
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
}
