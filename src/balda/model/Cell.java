/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package balda.model;

import java.awt.Point;

/**
 *
 * @author Ngo Nghia
 */
public class Cell {
    private Point _position;
    private char _letter;
    
    Cell(){
        _letter = ' ';
    }
    
    void setPosition(Point pos){
        _position = pos;
    }
    
    public Point position(){
        return _position;
    }
    
    public char letter(){
        return _letter;
    }
    
    public void setLetter(char l){
        _letter = l;
    }
    
    public boolean isAvailable(){
        return !('A' <= _letter && _letter <= 'Z');
    }
    
    @Override
    public boolean equals(Object other){
        Cell _other = (Cell) other;
        return position().equals(_other.position());
    }
}
