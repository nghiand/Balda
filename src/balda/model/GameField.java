package balda.model;

import java.awt.Point;
import java.util.ArrayList;

public class GameField {
    private int _width;
    private int _height;
    
    private ArrayList<Cell> _listCells = new ArrayList<>();
    
    public GameField(){}
    
    public GameField(int width, int height){
        setSize(width, height);
    }
    
    public void setSize(int width, int height){
        _width = width;
        _height = height;
    }
    
    public int width(){
        return _width;
    }
    
    public int height(){
        return _height;
    }
    
    public Cell cell(Point pos){
        for (Cell c: _listCells){
            if (c.position().equals(pos)) return c;
        }
        return null;
    }
    
    void setCell(Point pos, Cell cell){
        cell.setPosition(pos);
        _listCells.add(cell);
    }
    
    public void clear(){
        _listCells.clear();
    }
    
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
        
    boolean inRange(Point pos){
        return 1 <= pos.x && pos.x <= _height && 1 <= pos.y && pos.y <= _width;
    }
    
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
