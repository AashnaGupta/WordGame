/**
 * @author  Aashna Gupta
 * @version 1.0
 */
public class WordGuesser {
    private String[][] playingField;
    private int round;
    private String solution;

    public WordGuesser(String solution) {
        this.solution = solution;
        this.round = 1;
        this.playingField = new String[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++)
                playingField[i][j] = " ";
        }
    }

    public String[][] getPlayingField() {
        return playingField;
    }

    public void setPlayingField(String[][] playingField) {
        this.playingField = playingField;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public boolean checkGuess(String guess) throws InvalidGuessException {

        for (int i = 0; i < 5; i++) {
            if (guess.charAt(i) == solution.charAt(i)) {
                playingField[round - 1][i] = "'" + solution.charAt(i) + "'";
            } else {
                boolean in = false;
                for (int j = 0; j < 5; j++) {
                    if (guess.charAt(i) == solution.charAt(j))
                        if (guess.charAt(j) != solution.charAt(j))
                            in = true;
                }
                if (in)
                    playingField[round - 1][i] = "*" + guess.charAt(i) + "*";
                else
                    playingField[round - 1][i] = "{" + guess.charAt(i) + "}";
            }
        }

        if (guess.length() != 5)
            throw new InvalidGuessException("Invalid Guess!");

        if (guess.equals(solution)) {
            return true;
        } else {
            return false;
        }
    }

    public void printField() {
        for (int i = 0; i < playingField.length; i++) {
            for (int j = 0; j < playingField[i].length - 1; j++) {
                System.out.print(playingField[i][j] + " | ");
            }
            System.out.print(playingField[i][4]);
            System.out.println();
        }
    }

}
