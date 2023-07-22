import java.io.*;
import java.util.ArrayList;
import java.util.Random;
/**
 * @author  Aashna Gupta
 * @version 1.0
 */
public class WordLibrary {

    private String[] library;
    private int seed;
    private Random random;

    private String fileName;


    public WordLibrary(String fileName) throws FileNotFoundException {
        this.fileName = fileName;
        File f = new File(fileName);
        ArrayList<String> data = new ArrayList<>();
        BufferedReader bfr = new BufferedReader(new FileReader(f));
        try {
            String line = bfr.readLine();
            while (line != null) {
                data.add(line);
                line = bfr.readLine();
            }
            bfr.close();
            library = new String[data.size() - 1];
            seed = Integer.parseInt(data.get(0).substring(6));
            random = new Random(seed);
            for (int i = 0; i < data.size() - 1; i++) {
                library[i] = data.get(i + 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bfr != null)
                try {
                    bfr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        processLibrary();
    }

    public void verifyWord(String word) throws InvalidWordException {
        if (word.length() != 5)
            throw new InvalidWordException("Invalid word!");
    }

    public void processLibrary() {
        ArrayList<String> data = new ArrayList<>();
        for (int i = 0; i < library.length; i++) {
            data.add(library[i]);
        }

        for (int i = 0; i < data.size(); i++) {
            try {
                verifyWord(data.get(i));
            } catch (InvalidWordException e) {
                data.remove(i);
                System.out.println(e.getMessage());
            }
        }
        library = new String[data.size()];
        for (int i = 0; i < data.size(); i++) {
            library[i] = data.get(i);
        }
    }

    public String chooseWord() {
        return library[random.nextInt(library.length)];
    }

    public String[] getLibrary() {
        return library;
    }

    public void setLibrary(String[] library) {
        this.library = library;
    }

    public int getSeed() {
        return seed;
    }

    public void setSeed(int seed) {
        this.seed = seed;
    }
}
