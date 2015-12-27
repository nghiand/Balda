package balda.model;

import balda.model.events.PlayerActionEvent;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Timer;

/**
 * Class for description computer player
 */
public class ComputerPlayer extends AbstractPlayer{
    private int _difficulty;
    private Word bestWord = new Word();
    private char bestLetter;
    
    /**
     * Constructor
     * @param difficulty Difficulty of AI
     * @param field Game field
     * @param database Database dictionary
     * @param used Common dictionary, in which used words
     */
    public ComputerPlayer(int difficulty, GameField field, 
            Database database, UsedDictionary used){
        super("Computer", field, database, used);
        _difficulty = difficulty;
    }
    
    /**
     * Find best word to submit
     */
    public void move(){
        bestWord.clear();
        for (int row = 1; row <= _field.height(); row++){
            for (int col = 1; col <= _field.width(); col++){
                Point pos = new Point(row, col);
                if (_field.isAvailable(pos)){
                    Cell c = _field.cell(pos);
                    for (char ch = 'A'; ch <= 'Z'; ch++){
                        tryLetter(ch, c);
                    }
                }
            }
        }
        
        // could not find any word
        if (bestWord.getScore() == 0){
            skipTurn();
        } else{
            setAddingLetter(true);
            Cell chosenCell = bestWord.getCell(bestWord.getScore() - 1);
            int delay = 1000;
            int timer = delay;
            Timer addingLetter = new Timer(timer += delay, null);
            addingLetter.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    addLetter(chosenCell.position());
                    addingLetter.stop();
                }
            });
            addingLetter.start();

            Timer settingLetter = new Timer(timer += delay, null);
            settingLetter.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    setLetter(bestLetter);
                    setAddingLetter(false);
                    settingLetter.stop();
                }
            });
            settingLetter.start();


            for (int i = 0; i < bestWord.getScore(); i++){
                Point pos = bestWord.getCell(i).position();
                Timer appenddingLetter = new Timer(timer += delay, null);
                appenddingLetter.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        appendLetter(pos);
                        appenddingLetter.stop();
                    }
                });
                appenddingLetter.start();
            }

            Timer submittingWord = new Timer(timer += delay, null);
            submittingWord.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    submitWord();
                    submittingWord.stop();
                }
            });
            submittingWord.start();        


    //        addLetter(chosenCell.position());
    //        setLetter(bestLetter);
    //        setAddingLetter(false);
    //        for (int i = 0; i < bestWord.getScore(); i++){
    //            Point pos = bestWord.getCell(i).position();
    //            appendLetter(pos);
    //        }
    //        submitWord();
        }
    }
    
    private void wait(int time){
        try {
            //thread to sleep for the specified number of milliseconds
            Thread.sleep(time);
        } catch ( java.lang.InterruptedException ie) {
            System.out.println(ie);
        }        
    }
    
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
        Word currentWord = new Word();
        for (int i = listCells.size() - 1; i >= 0; i--){
            currentWord.addLetter(listCells.get(i));
        }
        if (currentWord.getScore() > bestWord.getScore()){
            bestWord = currentWord;
            bestLetter = lastLetter;
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
