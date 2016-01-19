package balda.model;

import java.awt.Point;
import java.util.ArrayList;

/**
 * Game Field
 */
public class GameField {
    private int _width;
    private int _height;
    
    private ArrayList<Cell> _listCells = new ArrayList<>();
    
    /**
     * Default constructor
     */
    public GameField(){}
    
    /**
     * Constructor with size
     * @param width Width
     * @param height Height
     */
    public GameField(int width, int height){
        setSize(width, height);
    }
    
    /**
     * Generate field
     * @param width Width
     * @param height Height
     */
    void generateField(int width, int height){
        clear();
        setSize(width, height);
        for (int row = 1; row <= height; row++){
            for (int col = 1; col <= width; col++){
                setCell(new Point(row, col), new Cell());
            }
        }        
    }
    
    /**
     * Set size for game field
     * @param width Width
     * @param height Height
     */
    public void setSize(int width, int height){
        _width = width;
        _height = height;
    }
    
    /**
     * Get width of game field
     * @return width
     */
    public int width(){
        return _width;
    }
    
    /**
     * Get height of game field
     * @return height
     */
    public int height(){
        return _height;
    }
    
    /**
     * Get cell in position
     * @param pos Position
     * @return cell
     */
    public Cell cell(Point pos){
        for (Cell c: _listCells){
            if (c.position().equals(pos)) return c;
        }
        return null;
    }
    
    /**
     * Set cell to position
     * @param pos Position
     * @param cell Cell
     */
    void setCell(Point pos, Cell cell){
        cell.setPosition(pos);
        _listCells.add(cell);
    }
    
    /**
     * Delete list cells
     */
    public void clear(){
        _listCells.clear();
    }
    
    /**
     * Check that can add a letter to cell in position
     * @param pos Position
     * @return true if can add a letter to cell
     */
    public boolean isAvailable(Point pos){
        Cell c = cell(pos);
        if (!c.isAvailable()) return false;
        boolean good = false;
        int dx[] = {-1, 0, 1, 0};
        int dy[] = {0, 1, 0, -1};
        for (int i = 0; i < 4; i++){            
            Cell neighbor = cell(new Point(pos.x + dx[i], pos.y + dy[i]));
            if (neighbor != null)
                good = good | !neighbor.isAvailable();
        }
        return good;
    }
    
    /**
     * Check if a position in game field
     * @param pos position
     * @return 
     */
    boolean inRange(Point pos){
        return 1 <= pos.x && pos.x <= _height && 1 <= pos.y && pos.y <= _width;
    }
    
    /**
     * Check if all cells were filled
     * @return true if game field is full
     */
    public boolean isFull(){
        int ret = 0;
        for (Cell c : _listCells){
            if (!isAvailable(c.position())) ret++;
        }
        if (ret == _width * _height)
            return true;
        return false;
    }
}
