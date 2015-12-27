package balda.model;

import java.awt.Point;

/**
 *  Class Cell for description Cell in game field
 * 
 */
public class Cell {
    private Point _position;
    private char _letter;
    
    /**
     * Constructor
     */
    Cell(){
        _letter = ' ';
    }
    
    /**
     * Set position for cell
     * @param pos position
     */
    void setPosition(Point pos){
        _position = pos;
    }
    
    /**
     * Get position
     * @return position
     */
    public Point position(){
        return _position;
    }
    
    /**
     * Get letter, which is in current cell
     * @return letter
     */
    public char letter(){
        return _letter;
    }
    
    /**
     * Set letter to current cell
     * @param l letter
     */
    public void setLetter(char l){
        _letter = l;
    }
    
    /**
     * Check is any letter set to current cell
     * @return true if current cell is set a letter
     */
    public boolean isAvailable(){
        return !('A' <= _letter && _letter <= 'Z');
    }
    
    @Override
    public boolean equals(Object other){
        Cell _other = (Cell) other;
        return position().equals(_other.position());
    }
}
