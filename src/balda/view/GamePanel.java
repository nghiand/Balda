package balda.view;

import balda.model.AbstractPlayer;
import balda.model.Cell;
import balda.model.GameMode;
import balda.model.GameModel;
import balda.model.events.GameEvent;
import balda.model.events.GameListener;
import balda.model.events.KeyboardEvent;
import balda.model.events.KeyboardListener;
import balda.model.events.PlayerActionEvent;
import balda.model.events.PlayerActionListener;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class GamePanel extends JFrame{
    
    
    private JPanel fieldPanel = new JPanel();
    private JLabel playerInfo = new JLabel();
    
    private JMenuBar _menu = null;
    private final String fileItems[] = new String [] {"New", "Exit"};
    
    private final int CELL_SIZE = 50;
    private final int TITLE_SIZE = 40;
    
    private GameModel _model;
    private int _width;
    private int _height;
    private GameMode _gameMode;
    private JLabel scores[] = new JLabel[] {new JLabel("0"), new JLabel("0")};
    private Keyboard _keyboard = new Keyboard();
    JPanel functions;
    
    
    // constructor
    public GamePanel(){
        super();
        
        this.setTitle("Balda");
        
        createMenu();
        setJMenuBar(_menu);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Box mainBox = Box.createVerticalBox();
        
        mainBox.add(Box.createVerticalStrut(10));
        
        fieldPanel.setDoubleBuffered(true);
        
        Dimension fieldDimension = new Dimension(200, 200);
        
        fieldPanel.setPreferredSize(fieldDimension);
        fieldPanel.setMinimumSize(fieldDimension);
        fieldPanel.setMaximumSize(fieldDimension);
        mainBox.add(fieldPanel);
        
                        
        setContentPane(mainBox);
        pack();
        setResizable(false);
    }
    
    
    private void start(){
        Box mainBox = Box.createVerticalBox();
        mainBox.add(createInfoPanel());
                
        // play field
        mainBox.add(Box.createVerticalStrut(10));
        createField();
        mainBox.add(fieldPanel);
        
        mainBox.add(Box.createVerticalStrut(10));
        _keyboard.addKeyboardListener(new KeyPressedListener());
        mainBox.add(_keyboard);
        mainBox.add(Box.createVerticalStrut(10));
        
        _keyboard.setEnabled(false);
        setEnabledField(true);
        setEnabledMenuFunctions(true);
        setContentPane(mainBox);
        pack();
        setResizable(false);        
    }
    
    // creating field
    private void createField(){
        fieldPanel.setDoubleBuffered(true);
        fieldPanel.setLayout(new GridLayout(_model.field().height(), _model.field().width()));
        
        Dimension fieldDimension = new Dimension(CELL_SIZE * _model.field().width(), CELL_SIZE * _model.field().height());
        
        fieldPanel.setPreferredSize(fieldDimension);
        fieldPanel.setMinimumSize(fieldDimension);
        fieldPanel.setMaximumSize(fieldDimension);
        
        repaintField();
    }
    
    public void repaintField(){
        fieldPanel.removeAll();
        for (int row = 1; row <= _model.field().height(); row++){
            for (int col = 1; col <= _model.field().width(); col++){
                JButton button = new JButton("");
                button.setFocusable(false);
                fieldPanel.add(button);
                button.addActionListener(new ClickListener());
            }
        }
    }
    
    // creating info panel
    private Box createInfoPanel(){
        Box mainBox = Box.createVerticalBox();
        Box playerBox = Box.createHorizontalBox();
        
        playerBox.add(Box.createHorizontalStrut(50));
                
        playerInfo.setText("?");
        playerBox.add(playerInfo);
        
        playerBox.add(Box.createHorizontalStrut(50));
        //box.add(Box.createVerticalStrut(10));
        
        // scoreboard
        Box scoreboard = Box.createVerticalBox();
        scoreboard.add(new JLabel("Scoreboard: "));
        JPanel firstPlayerScore = new JPanel();
        firstPlayerScore.add(new JLabel("Player 1: "));
        scores[0].setText("0");
        firstPlayerScore.add(scores[0]);
        JPanel secondPlayerScore = new JPanel();
        secondPlayerScore.add(new JLabel("Player 2: "));
        scores[1].setText("0");
        secondPlayerScore.add(scores[1]);
        
        scoreboard.add(firstPlayerScore);
        scoreboard.add(secondPlayerScore);
        
        playerBox.add(scoreboard);
        
        mainBox.add(playerBox);
        
        functions = new JPanel();
        JButton skip = new JButton("Skip turn");
        skip.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                _model.exchangePlayer();
            }
        });
        JButton addWord = new JButton("Add to dictionary");
        
        JButton submit = new JButton("Submit");
        submit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                _model.activePlayer().submitWord();
            }
        });
        
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                _model.activePlayer().currentWord().clear();
                resetCellColor();
            }
        });
        
        functions.add(skip);
        functions.add(addWord);
        functions.add(submit);
        functions.add(cancel);
        
        mainBox.add(functions);
                
        return mainBox;
    }
    
    
    // create menu
    private void createMenu(){
        _menu  = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        
        for (int i = 0; i < fileItems.length; i++){
            JMenuItem item = new JMenuItem(fileItems[i]);
            item.setActionCommand(fileItems[i].toLowerCase());
            item.addActionListener(new NewMenuListener());
            fileMenu.add(item);
        }
        
        fileMenu.insertSeparator(1);
        _menu.add(fileMenu);
    }
    
    public class NewMenuListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            String command = e.getActionCommand();
            if ("exit".equals(command)){
                System.exit(0);
            }
            if ("new".equals(command)){
                createGameOption();
                _model = new GameModel(_width, _height, _gameMode);
                _model.addPlayerActionListener(new PlayerObserve());
                _model.addGameListener(new GameObserve());
                start();
                _model.start();
            }
        }
    }
    
    private void createGameOption(){
        // game field's size
        JSpinner widthBox = new JSpinner(new SpinnerNumberModel(5, 3, 10, 1));
        JSpinner heightBox = new JSpinner(new SpinnerNumberModel(5, 3, 10, 1));
        
        JPanel size = new JPanel();
        size.add(new JLabel("Width"));
        size.add(widthBox);
        
        size.add(Box.createHorizontalStrut(10));
        
        size.add(new JLabel("Height"));
        size.add(heightBox);
        
        // game mode
        JPanel gameMode = new JPanel();
        gameMode.add(new JLabel("Game mode"));
        
        // ai mode
        String aiModeString[] = {"Easy", "Normal", "Hard"};
        JComboBox aiModeList = new JComboBox(aiModeString);
        aiModeList.setSelectedIndex(0);
        aiModeList.setEnabled(false);
        
        // 2 players
        JRadioButton twoPlayersMode = new JRadioButton("2 players");
        twoPlayersMode.setActionCommand("2 players");
        twoPlayersMode.setSelected(true);
        twoPlayersMode.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                aiModeList.setEnabled(false);
            }
        });
        
        // vs computer
        JRadioButton vsComputerMode = new JRadioButton("Vs computer");
        vsComputerMode.setActionCommand("vs computer");
        vsComputerMode.setSelected(false);
        vsComputerMode.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                aiModeList.setEnabled(true);
            }
        });
        
        // group game mode
        ButtonGroup groupGameMode = new ButtonGroup();
        groupGameMode.add(twoPlayersMode);
        groupGameMode.add(vsComputerMode);
        
        gameMode.add(twoPlayersMode);
        gameMode.add(vsComputerMode);
        gameMode.add(aiModeList);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(size);
        mainPanel.add(gameMode);
        
        JOptionPane.showMessageDialog(null, mainPanel, "Game setting", JOptionPane.INFORMATION_MESSAGE);
        
        _width = (int) widthBox.getValue();
        _height = (int) heightBox.getValue();
        if (twoPlayersMode.isSelected()){
            _gameMode = GameMode.TWO_PLAYERS;
        } else{
            int index = aiModeList.getSelectedIndex();
            if (index == 0)
                _gameMode = GameMode.EASY;
            else if (index == 1)
                _gameMode = GameMode.NORMAL;
            else
                _gameMode = GameMode.HARD;
        }
    }
    
    
    private void addStartingWord(String word){
        int l = word.length();
        int row = (_model.field().height() + 1) / 2;
        int col = (_model.field().width() - l) / 2 + 1;
        for (int i = 0; i < l; i++)
            drawLetterOnField(new Point(row, col + i), word.charAt(i));
    }

    
    private Point buttonPosition(JButton btn){
        int index = 0;
        for (Component widget : fieldPanel.getComponents()){
            if (widget instanceof JButton){
                if (btn.equals((JButton)widget)){
                    break;
                }
                index++;
            }
        }
        int width = _model.field().width();
        return new Point(index / width + 1, index % width + 1);
    }
    
    private JButton getButton(Point pos){
        int index = _model.field().width() * (pos.x - 1) + (pos.y - 1);
        
        for (Component widget : fieldPanel.getComponents()){
            if (widget instanceof JButton){
                if (index == 0){
                    return (JButton) widget;
                }
                index--;
            }
        }
        return null;
    }

    
    private void drawLetterOnField(Point pos, char letter){
        JButton button = getButton(pos);
        button.setText(String.valueOf(letter));
        changeCellColor(pos, false);
    }    
    
    private void drawPlayerOnInfoPanel(AbstractPlayer player){
        String name = player.name();
        playerInfo.setText(name);
    }
    
    private void showMessage(String message){
        JOptionPane.showMessageDialog(new JFrame(), message);
    }
    
    private void updateScoreboard(AbstractPlayer player){
        int score = player.getScore();
        String name = player.name();
        if (name == "Player 1"){
            scores[0].setText(Integer.toString(score));
        } else{
            scores[1].setText(Integer.toString(score));            
        }
    }
    
    private void changeCellColor(Point pos, boolean mark){
        JButton button = getButton(pos);
        if (mark)
            button.setBackground(Color.yellow);
        else
            button.setBackground(null);
    }
    
    private void resetCellColor(){
         for (Component widget : fieldPanel.getComponents()){
            if (widget instanceof JButton){
                ((JButton)widget).setBackground(null);
            }
        }
    }
    
    private void setEnabledField(boolean on){
        Component comp[] = fieldPanel.getComponents();
        for (Component c : comp){
            c.setEnabled(on);
        }
    }
    
    private void setEnabledMenuFunctions(boolean on){
        Component comp[] = functions.getComponents();
        for (Component c : comp){
            c.setEnabled(on);
        }
    }
    
    
    public class KeyPressedListener implements KeyboardListener{
        @Override
        public void keyIsPressed(KeyboardEvent e){
            //System.out.println(e.getKey());
            _model.activePlayer().setLetter(e.getKey().charAt(0));
            _keyboard.setEnabled(false);
        }
    }
    
    // listening to key press on field
    private class ClickListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            JButton button = (JButton)e.getSource();
            
            Point p = buttonPosition(button);
            
            _model.clickOnCell(p);
        }
    }
    
    // player action listener
    private class PlayerObserve implements PlayerActionListener{
        @Override
        public void letterIsPlaced(PlayerActionEvent e){
            drawLetterOnField(e.cell().position(), e.cell().letter());
        }
        
        @Override
        public void letterIsAppended(PlayerActionEvent e){
            changeCellColor(e.cell().position(), true);
        }
        
        @Override
        public void freeCellIsChoosen(PlayerActionEvent e){
            resetCellColor();
            _keyboard.setEnabled(true);
            changeCellColor(e.cell().position(), true);
        }

        @Override
        public void wordNotContainsCell(PlayerActionEvent e) {
            showMessage("Your word doesn't contains your choosen cell");            
        }

        @Override
        public void usedWord(PlayerActionEvent e) {
            showMessage("This word was used!");            
        }

        @Override
        public void nonexistentWord(PlayerActionEvent e) {
            showMessage("This word is not in our dictionary!");            
        }

        @Override
        public void wordIsSubmitted(PlayerActionEvent e) {
            updateScoreboard(e.player());            
        }
    }
    
    private class GameObserve implements GameListener{
        @Override
        public void gameFinished(GameEvent e){
            if (e.word() != null){
                if (e.word() == "Draw"){
                    showMessage("Draw game!");
                } else showMessage(e.word() + " won!");
                
                setEnabledField(false);
                setEnabledMenuFunctions(false);
            }
        }
        
        @Override
        public void playerExchanged(GameEvent e){
            drawPlayerOnInfoPanel(e.player());
            resetCellColor();
        }
        
        @Override
        public void startingWordIsChoosen(GameEvent e){
            String word = e.word();
            addStartingWord(word);
        }
    }
}
