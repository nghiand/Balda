package balda.model;

import java.util.ArrayList;

/**
 * Word - List of chosen cells
 */
public class Word {
    String _word;
    ArrayList _listCells = new ArrayList<>();
    
    /**
     * Constructor
     */
    public Word(){
        _word = "";
    }
    
    /**
     * Get length of word
     * @return length
     */
    public int getScore(){
        return _word.length();
    }
    
    /**
     * Check if Cell c is adjacent with last cell in word
     * @param c cell
     * @return true if cell c is adjacent with last cell in word
     */
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
    
    /**
     * Check if word contains cell c
     * @param c cells
     * @return true if word contains cell c
     */
    public boolean contains(Cell c){
        for (Object cell : _listCells){
            if (((Cell) cell).equals(c)) return true;
        }
        return false;
    }
    
    /**
     * Add a cell to current word
     * @param c cell
     */
    public void addLetter(Cell c){
        _word += c.letter();
        _listCells.add(c);
    }
    
    /**
     * Remove all cell in word
     */
    public void clear(){
        _word = "";
        _listCells.clear();
    }
    
    /**
     * Get current word
     * @return word
     */
    public String word(){
        return _word;
    }
    
    /**
     * Get cell in position
     * @param i position
     * @return cell
     */
    Cell getCell(int i){
        if (i >= _listCells.size()) return null;
        return (Cell)_listCells.get(i);
    }
}
