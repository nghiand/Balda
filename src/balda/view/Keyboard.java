/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package balda.view;

import balda.model.events.KeyboardEvent;
import balda.model.events.KeyboardListener;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Ngo Nghia
 */
public class Keyboard extends JPanel{
    private String letters[][] = {
        {"Q","W","E","R","T","Y","U","I","O","P"},
        {"A","S","D","F","G","H","J","K","L"},
        {"Z","X","C","V","B","N","M",}
    };
    
    JButton buttons[][] = new JButton[3][];
    
    public Keyboard(){
        super(new GridLayout(3, 0));
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        Dimension size = new Dimension(50, 50);
        
        for (int row = 0; row < 3; row++){
            buttons[row] = new JButton[letters[row].length];
            JPanel panel = new JPanel(new GridLayout(1, letters[row].length));
            panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
            for (int i = 0; i < letters[row].length; i++){
                JButton btn = new JButton(letters[row][i]);    
                btn.setPreferredSize(size);
                btn.setMinimumSize(size);
                btn.setMaximumSize(size);
                btn.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e){
                        fireKeyIsPressed(btn.getText());
                    }
                });

                buttons[row][i] = btn;
                panel.add(buttons[row][i]);
            }
            this.add(panel);
        }
    }
    
    private ArrayList _keyboardListenerList = new ArrayList();
    
    public void addKeyboardListener(KeyboardListener l){
        _keyboardListenerList.add(l);
    }
    
    protected void fireKeyIsPressed(String key){
        for (Object listener : _keyboardListenerList){
            KeyboardEvent e = new KeyboardEvent(this);
            e.setKey(key);
            ((KeyboardListener) listener).keyIsPressed(e);
        }
    }
    
    public void setEnabled(boolean on){
        for (int i = 0; i < buttons.length; i++){
            for (int j = 0; j < buttons[i].length; j++)
                buttons[i][j].setEnabled(on);
        }
    }
}
