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
public abstract class ComputerPlayer extends AbstractPlayer{    
    protected Word bestWord = new Word();
    protected char bestLetter;
    protected Cell bestCell = new Cell();
    
    abstract Cell chooseACell();
    abstract char chooseALetter(Cell choosenCell);
    abstract Word chooseAWord(Cell choosenCell, char addedLetter);
    
    /**
     * Constructor
     * @param field Game field
     * @param dictionary Dictionary
     * @param used Common dictionary, in which used words
     */
    public ComputerPlayer(GameField field, Dictionary dictionary, UsedWords used){
        super("Computer", field, dictionary, used);
    }
    
    /**
     * Find best word to submit
     */
    public void move(){
        bestWord.clear();
        bestCell = chooseACell();
        if (bestCell != null){
            bestLetter = chooseALetter(bestCell);
            bestWord = chooseAWord(bestCell, bestLetter);
        }
        
        // could not find any word
        if (bestCell == null){
            skipTurn();
        } else{
            bestWord.addLetter(bestCell);
            setAddingLetter(true);
            int delay = 1000;
            int timer = delay;
            Timer addingLetter = new Timer(timer += delay, null);
            addingLetter.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    chooseCell(bestCell.position());
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
                        appendLetterToWord(pos);
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
}
