class Sevens {
    // GVdie d1, d2, d3, d4, d5, d6;
    GVdie[] dice; 
    int scoreP1, scoreP2;
    int numRolls;
    boolean isP1Turn;
    int TURNS = 3;
    
    public Sevens() {
        dice = new GVdie[] { new GVdie(), new GVdie(), new GVdie(), new GVdie(), new GVdie(), new GVdie() };
        for (GVdie die : dice) {
            die.setBlank();
        }
        isP1Turn = true;
        scoreP1 = 0;
        scoreP2 = 0;
        numRolls = 0;
    }
    // accessors
    
    public int getRolls() {
        return numRolls;
    }
    public int getScore1() {
        return scoreP1;
    }
    public int getScore2() {
        return scoreP2;
    }
    public boolean isPlayer1turn() {
        return isP1Turn;
    }
    public boolean turnOver() {
        if (numRolls == TURNS) 
            return true;
        else
            return false;
    }
    public boolean gameOver() {
        if (getScore1() >= 77 || getScore2() >= 77)
            return true;
        else 
            return false;
    }
    // NOTE: this method is required in project spec, 
    // redundant here due to initializing them in an array
    public GVdie getDie(int num) {
        return dice[num - 1];
    }
    // helper methods  
    private void resetDice() {
        for (GVdie die : dice) {
            die.setBlank();
            die.setFrozen(false);
            die.setHeld(false);
        }
    }
    private int getDiceTotal() {
        int total = 0;
        for (GVdie die : dice) {
            if (!die.isHeld())
                total += die.getValue();
        }
        return total;
    }
    private int getHeldTotal() {
        int total = 0;
        for (GVdie die : dice) {
            if (die.isHeld())
                total += die.getValue();
        }
        return total;
    }
    private int getNumHeld() {
        int total = 0;
        for (GVdie die : dice) {
            if (die.isHeld())
                total++;
        }
        return total;
    }
    private void freezeDice() {
        for (GVdie die : dice) {
            die.setFrozen(true);
        }
    }
    private void showDice() {
        for (GVdie die : dice) {
            if (die.isHeld())
                System.out.print("* ");
            else
                System.out.print(die.getValue() + " ");
        }
        System.out.println();
    }
    // mutator methods
    public void rollDice() {
        if (!turnOver() && isValidHand()) {
            for (GVdie die : dice) {
                if (!die.isHeld())
                    die.roll();
            }
            numRolls++;
            
            checkValidOptions();
        }
    }
    public void passDice() {
        // update score, change player, reset rolls, reset dice
        if (isPlayer1turn()) 
            scoreP1 += getDiceTotal();
        else
            scoreP2 += getDiceTotal();
        
        isP1Turn = !isP1Turn;
        numRolls = 0;
        resetDice();
    }
    public void resetGame() {
        isP1Turn = true;
        scoreP1 = 0;
        scoreP2 = 0;
        numRolls = 0;
        resetDice();
    }
    // preventing user error
    private void checkValidOptions() {
        boolean hasMove = false;
        checkLoop:
        for (int i = 0; i < dice.length; i++) {
            if (!dice[i].isHeld())
                for (int j = i + 1; j < dice.length; j++) {
                    if (!dice[j].isHeld() && dice[i].getValue() + dice[j].getValue() == 7) {
                        hasMove = true;
                        break checkLoop;
                    }
                }
        }
        if (!hasMove) {
            numRolls = TURNS;
            freezeDice();
        }
    }
    private boolean isValidHand() {
        if (numRolls == 0 && getNumHeld() == 0)
            return true;
        else if (numRolls == 1 && getNumHeld() == 2 && getHeldTotal() == 7) 
            return true;
        else if (numRolls == 2 && getNumHeld() == 4 && getHeldTotal() == 14)
            return true;
        else
            return false;
    }
    // methods for testing
    private void autoHold() {
       boolean didMove = false;
       checkLoop:
       for (int i = 0; i < dice.length; i++) {
           if (!dice[i].isHeld()) 
                for (int j = i + 1; j < dice.length; j++) {
                    if (!dice[j].isHeld() && dice[i].getValue() + dice[j].getValue() == 7) {
                        dice[i].setHeld(true);
                        dice[j].setHeld(true);
                        didMove = true;
                        break checkLoop;
                    }
                }
        }
        if (didMove == false)
            numRolls = TURNS;
    }
    private void autoTurn() {
        while(!turnOver()) {
            rollDice();
            showDice();
            autoHold();
        }
        passDice();
    }
    public void autoGame() {
        while (!gameOver()) {
            System.out.println(String.format("Player %d turn, Score: %d", 
                                isPlayer1turn() ? 1 : 2, 
                                isPlayer1turn() ? getScore1() : getScore2()
                                ));
            autoTurn();
            System.out.println(String.format("New Score: %d", 
                                            !isPlayer1turn() ? getScore1() : getScore2())
                                            );
            System.out.println();
        }
        System.out.println("Game Over");
        System.out.println(String.format("Player 1: %d", getScore1()));
        System.out.println(String.format("Player 2: %d", getScore2()));
        System.out.println(String.format("Player %d Wins!", isPlayer1turn() ? 1 : 2));
        resetGame();
    }
}





























