import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
/**
 * @author  Aashna Gupta
 * @version 1.0
 */
public class WordGame {

    public static String welcome = "Ready to play?";
    public static String yesNo = "1.Yes\n2.No";
    public static String noPlay = "Maybe next time!";
    public static String currentRoundLabel = "Current Round: ";
    public static String enterGuess = "Please enter a guess!";
    public static String winner = "You got the answer!";
    public static String outOfGuesses = "You ran out of guesses!";
    public static String solutionLabel = "Solution: ";
    public static String incorrect = "That's not it!";
    public static String keepPlaying = "Would you like to make another guess?";
    public static String fileNameInput = "Please enter a filename";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(fileNameInput);
        String filename = scanner.nextLine();
        WordLibrary myLib = null;
        try {
            myLib = new WordLibrary(filename);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String input;
        int currentRound;

        while (true) {
            System.out.println(welcome);
            currentRound = 1;
            System.out.println(yesNo);
            input = scanner.nextLine();
            String word = myLib.chooseWord();
            WordGuesser game = new WordGuesser(word);
            ArrayList<String> guesses = new ArrayList<>();
            if (input.equals("1")) {
                while (true) {

                    System.out.println(currentRoundLabel + currentRound);
                    game.printField();
                    currentRound++;
                    System.out.println(enterGuess);
                    String guess = scanner.nextLine();
                    guesses.add(guess);
                    boolean rv = false;
                    try {
                        rv = game.checkGuess(guess);
                    } catch (InvalidGuessException e) {
                        System.out.println(e.getMessage());
                    }
                    if (rv) {
                        System.out.println(winner);
                        game.printField();
                        break;
                    } else {
                        if (currentRound == 6) {
                            System.out.println(outOfGuesses);
                            System.out.println(solutionLabel + word);
                            game.printField();
                            currentRound = 1;
                            break;
                        }
                        System.out.println(incorrect);
                        System.out.println(keepPlaying);
                        System.out.println(yesNo);
                        input = scanner.nextLine();
                        if (input.equals("2")) {
                            game.printField();
                            break;
                        }
                    }
                    game.setRound(game.getRound() + 1);
                }
            } else {
                System.out.println(noPlay);
                break;
            }
            String[] arr = new String[guesses.size()];
            for (int i = 0; i < guesses.size(); i++)
                arr[i] = guesses.get(i);
            try {
                updateGameLog(word, arr, game.checkGuess(arr[arr.length - 1]));
            } catch (InvalidGuessException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void updateGameLog(String solution, String[] guesses, boolean solved) {
        try {
            File f = new File("gamelog.txt");
            if (f.createNewFile()) {
                FileReader fr = new FileReader(f);
                FileOutputStream fos = new FileOutputStream(f, false);
                PrintWriter pw = new PrintWriter(fos);
                pw.println("Games Completed: 1\nGame 1\n- Solution: "+solution);
                pw.print("- Guesses: ");
                pw.print(guesses[0]);
                if (guesses.length != 1) {
                    for (int i = 1; i < guesses.length; i++) {
                        pw.print(",");
                        pw.print(guesses[i]);
                    }
                }
                pw.println();
                pw.print("- Solved: ");
                if (solved) {
                    pw.println("Yes");
                } else {
                    pw.println("No");
                }
                pw.close();
            }
            else {
                FileReader fr = new FileReader(f);
                BufferedReader bfr = new BufferedReader(fr);
                ArrayList<String> data = new ArrayList<String>();
                String line = bfr.readLine();
                while (line != null) {
                    data.add(line);
                    line = bfr.readLine();
                }
                bfr.close();
                String[] compGame = data.get(0).split(" ");
                int games = Integer.parseInt(compGame[2]) + 1;
                data.set(0, "Games Completed: " + games);

                FileOutputStream fos = new FileOutputStream(f, false);
                PrintWriter pw = new PrintWriter(fos);
                for (int i = 0; i < data.size(); i++) {
                    pw.println(data.get(i));
                }
                pw.println("Game " + games);
                pw.println("- Solution: " + solution);
                pw.print("- Guesses: ");
                pw.print(guesses[0]);
                if (guesses.length != 1) {
                    for (int i = 1; i < guesses.length; i++) {
                        pw.print(",");
                        pw.print(guesses[i]);
                    }
                }
                pw.println();
                pw.print("- Solved: ");
                if (solved) {
                    pw.println("Yes");
                } else {
                    pw.println("No");
                }
                pw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
