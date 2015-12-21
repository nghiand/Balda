package balda.model;

import java.util.ArrayList;

public class Word {
    String _word;
    ArrayList _listCells = new ArrayList<>();
    
    public Word(){
        
    }
    
    public int getScore(){
        return _word.length();
    }
    
    public boolean isAdjacent(Cell c){
        if (_listCells.size() == 0) return true;
        Cell last = (Cell) _listCells.get(_listCells.size() - 1);
        int dx[] = {-1, 0, 1, 0};
        int dy[] = {0, 1, 0, -1};
        boolean good = false;
        for (int k = 0; k < 4; k++){
            if (last.position().x + dx[k] == c.position().x &&
                    last.position().y + dy[k] == c.position().y)
                good = true;
        }
        return good;
    }
    
    public boolean contains(Cell c){
        for (Object cell : _listCells){
            if (((Cell) cell).equals(c)) return true;
        }
        return false;
    }
    
    public void addLetter(Cell c){
        _word += c.letter();
        _listCells.add(c);
    }
    
    public void clear(){
        _word = "";
        _listCells.clear();
    }
    
    public String word(){
        return _word;
    }
}
