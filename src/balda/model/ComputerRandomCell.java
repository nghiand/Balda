package balda.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class ComputerRandomCell extends ComputerPlayer{
    public static final String name = "Normal";

    /**
     * Constructor
     * @param field Game field
     * @param dictionary dictionary
     * @param used Common dictionary, in which used words
     */
    public ComputerRandomCell(GameField field, 
            Dictionary dictionary, UsedWords used){
        super(field, dictionary, used);
    }    
    
    @Override
    Cell chooseACell(){
        ArrayList availableCellList = new ArrayList();
        for (int row = 1; row <= _field.height(); row++){
            for (int col = 1; col <= _field.width(); col++){
                Point pos = new Point(row, col);
                if (_field.isAvailable(pos)){
                    availableCellList.add(pos);
                }
            }
        }
        
        Random rd = new Random();
        int position = rd.nextInt(availableCellList.size());
        Point pos = (Point) availableCellList.get(position);
        currentCell = _field.cell(pos);
        return currentCell;
    }

    @Override
    char chooseALetter(Cell choosenCell) {
        currentWord.clear();
        currentCell = choosenCell;
        for (char ch = 'A'; ch <= 'Z'; ch++){
            tryLetter(ch, choosenCell);
        }
        return currentLetter;
    }

    @Override
    Word chooseAWord(Cell choosenCell, char addedLetter) {
        return currentWord;
    }

    private Cell currentCell = new Cell();
    private char currentLetter;
    private Word currentWord = new Word();
    private int _difficulty = 5;
    
    /**
     * Try to set letter to cell c
     * @param letter Letter
     * @param c Cell
     */
    private void tryLetter(char letter, Cell c){
        Word ret = new Word();
        ArrayList<Cell> listCells = new ArrayList<Cell>();
        listCells.add(c);
        findWord(0, c, listCells, letter);
    }
    
    /**
     * Get String word from array of chosen cells
     * @param listCells Array of chosen cells
     * @param lastLetter Letter, which was set to last cell
     * @return Word
     */
    private String toString(ArrayList<Cell> listCells, char lastLetter){
        String ret = "";
        for (int i = 1; i < listCells.size(); i++){
            ret = listCells.get(i).letter() + ret;
        }
        ret += lastLetter;
        return ret;
    }
    
    /**
     * Update best word after finding new word
     * @param listCells Array of chosen cells
     * @param lastLetter Letter, which was set to last cell
     */
    private void updateBestWord(ArrayList<Cell> listCells, char lastLetter){
        Word word = new Word();
        for (int i = listCells.size() - 1; i > 0; i--){
            word.addLetter(listCells.get(i));
        }
        if (word.getScore() > currentWord.getScore()){
            currentWord = word;
            currentLetter = lastLetter;
            currentCell = listCells.get(0);
        }
    }
    
    /**
     * Try to find new word
     * @param length Length of current word
     * @param currentCell Last chosen cell
     * @param listCells Array of chosen cells
     * @param lastLetter Letter, which was set to last cell
     */
    private void findWord(int length, Cell currentCell, ArrayList<Cell> listCells, char lastLetter){
        String word = toString(listCells, lastLetter);
        if (!_used.isInDictionary(word) && _database.isInDictionary(word)){
            updateBestWord(listCells, lastLetter);
        }
        
        //System.out.println(word);
        if (length >= _difficulty){
            return;
        }
        
        int dx[] = {-1, 0, 1, 0};
        int dy[] = {0, 1, 0, -1};
        for (int k = 0; k < 4; k++){
            Point pos = currentCell.position();
            Point newPos = new Point(pos.x + dx[k], pos.y + dy[k]);
            
            if (_field.inRange(newPos)){
                Cell newCell = _field.cell(newPos);
                if (!listCells.contains(newCell) && !newCell.isAvailable()){
                    listCells.add(newCell);
                    findWord(length + 1, newCell, listCells, lastLetter);
                    listCells.remove(listCells.size() - 1);
                }
            }
        }
    }
    
}
