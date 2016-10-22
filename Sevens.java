class Sevens {
    // GVdie d1, d2, d3, d4, d5, d6;
    GVdie[] dice; 
    int scoreP1, scoreP2;
    int numRolls;
    boolean isP1Turn;
    
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
    // NOTE project spec omits the roll number from vars to put behind getters
    public int getRolls() {
        return numRolls;
    }
    public int getScore1() {
        return scoreP1;
    }
    public int getScore2() {
        return scoreP2;
    }
    public boolean isPlayer1Turn() {
        return isP1Turn;
    }
    public boolean turnOver() {
        if (numRolls >= 3) 
            return true;
        else
            return false;
    }
    public boolean gameOver() {
        if (getScore1() >= 77 || getScore1() >= 77)
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
    }
    // mutator methods
    public void rollDice() {
        if (numRolls < 3) {
            for (GVdie die : dice) {
                if (!die.isHeld())
                    die.roll();
            }
            numRolls++;
        }
    }
    public void passDice() {
        // update score, change player, reset rolls, reset dice
        if (isPlayer1Turn()) 
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
        for (int i = 0; i < 6; i++) {
            if (!dice[i].isHeld())
                for (int j = i + 1; j < 6; j++) {
                    if (!dice[j].isHeld() && dice[i] + dice[j] == 7) {
                        hasMove = true;
                        break checkLoop;
                    }
                }
        }
        if (!hasMove) {
            numRolls = 3;
            freezeDice();
        }
    }
    private boolean isValidHand() {
        if (numRolls == 0 && 
    }
}





























