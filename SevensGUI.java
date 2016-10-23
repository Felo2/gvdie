import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*; 
/***********************************************
 * GUI for dice game called Sevens
 * 
 * @author Scott Grissom
 * @version June 15, 2016
 **********************************************/
public class SevensGUI extends JFrame implements ActionListener{

    /** the game that keeps track of everything */
    Sevens theGame;

    /** player scores */
    JLabel score1Label, score2Label;

    /** menu items */
    JMenuItem quitItem;
    JMenuItem newGameItem;

    /** buttons for each player action */
    JButton roll1Button, roll2Button;
    JButton pass1Button, pass2Button;

    public static void main(String [] args){
        SevensGUI gui = new SevensGUI();
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setTitle("Matthew Pische's Sevens Game");
        gui.pack();
        gui.setVisible(true);
    }

    /***************************************************************
    Position the six dice and buttons on the screen
     ***************************************************************/
    public SevensGUI(){  
        theGame = new Sevens();
        setLayout(new GridBagLayout());
        GridBagConstraints position = new GridBagConstraints();

        // display all dice using a fancy loop
        position = new GridBagConstraints();
        position.gridy = 0;
        position.gridx = 0;   
        position.insets = new Insets(10,5,10,5);
        for (int i=1; i<=6; i++){
            add(theGame.getDie(i), position);
            position.gridx++;
        }

        // score for player 1
        score1Label = new JLabel();
        position = new GridBagConstraints();
        position.gridx = 1;
        position.gridy = 2;
        add(score1Label, position);

        // roll button for player 1
        roll1Button = new JButton("roll");
        position.gridx = 1;
        position.gridy = 3;
        add(roll1Button, position);
        roll1Button.addActionListener(this); 

        // pass button for player 1
        pass1Button = new JButton("pass");
        position.gridx = 1;
        position.gridy = 4;
        add(pass1Button, position);
        pass1Button.addActionListener(this);

        // score label and buttons for Player 2
        
        score2Label = new JLabel();
        position = new GridBagConstraints();
        position.gridx = 4;
        position.gridy = 2;
        add(score2Label, position);
        
        roll2Button = new JButton("roll");
        position.gridx = 4;
        position.gridy = 3; 
        add(roll2Button, position);
        roll2Button.addActionListener(this);
        
        pass2Button = new JButton("pass");
        position.gridx = 4;
        position.gridy = 4;
        add(pass2Button, position);
        pass2Button.addActionListener(this);

        setupMenus();
        newGame();
    }   

    /*****************************************************************
    Handles all button and menu selections

    @param e the component that was pressed
     *****************************************************************/  
    public void actionPerformed (ActionEvent e){

        // player selected quit from menu
        if (e.getSource() == quitItem){
            System.exit(1);
        }

        // player selected new game from menu
        if (e.getSource() == newGameItem) {
            newGame();
        }
        // player clicked on one of the roll buttons
        if (e.getSource() == roll1Button || e.getSource() == roll2Button){
            theGame.rollDice();
            if(theGame.isPlayer1turn())
                pass1Button.setEnabled(true);
            else
                pass2Button.setEnabled(true);            
        }

        // player clicked on one of the pass buttons  
        // pass the dice    
        if (e.getSource() == pass1Button || e.getSource() == pass2Button){
            theGame.passDice();
            if (theGame.isPlayer1turn()) {
                roll1Button.setEnabled(true);
                roll2Button.setEnabled(false);
                pass1Button.setEnabled(false);
                pass2Button.setEnabled(false);
            } else {
                roll1Button.setEnabled(false);
                roll2Button.setEnabled(true);
                pass1Button.setEnabled(false);
                pass2Button.setEnabled(false);
            }
        }

        // disable both roll buttons if turn is over
        if (theGame.turnOver()) {
            roll1Button.setEnabled(false);
            roll2Button.setEnabled(false);
        }

        // update the scores
        score1Label.setText("Player 1: " + theGame.getScore1());
        score2Label.setText("Player 2: " + theGame.getScore2());

        // disable all buttons if game over.
        // display winning message using JOptionPane
        if (theGame.gameOver()) {
            roll1Button.setEnabled(false);
            roll2Button.setEnabled(false);
            pass1Button.setEnabled(false);
            pass2Button.setEnabled(false);
            JOptionPane.showMessageDialog(this, String.format("Player %d wins!", 
                                                                theGame.getScore1() > theGame.getScore2() ? 
                                                                2 : 1));                                              
        }
    }   

    /************************************************************
    Quick way to disable all buttons
     ************************************************************/
    private void disableAllButtons(){
        // disable buttons for player 1
        roll1Button.setEnabled(false);
        pass1Button.setEnabled(false); 

        //disable buttons for player 2
        roll2Button.setEnabled(false);
        pass2Button.setEnabled(false);
    }

    /************************************************************
    Reset the game, the score, and enable all buttons
     ************************************************************/
    private void newGame(){
        score1Label.setText("Player 1: " + theGame.getScore1());
        theGame.resetGame();

        disableAllButtons();
        // enable player 1 roll button, display score for players 
        roll1Button.setEnabled(true);
        score1Label.setText("Player 1: " + theGame.getScore1());
        score2Label.setText("Player 2: " + theGame.getScore2());
    }

    /************************************************************
    Setup the file menu with two options: quit and new game
    Menu items must register their Action Listeners
     ************************************************************/
    private void setupMenus (){

        JMenuBar menus = new JMenuBar();
        setJMenuBar(menus);

        JMenu fileMenu = new JMenu("File");
        menus.add(fileMenu);

        newGameItem = new JMenuItem("New Game");
        newGameItem.addActionListener(this);
        quitItem = new JMenuItem("Quit");
        quitItem.addActionListener(this);

        fileMenu.add(newGameItem);
        fileMenu.add(quitItem);
    }
}
