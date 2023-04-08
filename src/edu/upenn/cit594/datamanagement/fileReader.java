package edu.upenn.cit594.datamanagement;
import edu.upenn.cit594.util.State;
import edu.upenn.cit594.util.Tweet;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Abstract class that represents a file reader
 */
public abstract class fileReader {

    private String CSVFileName;

    public abstract ArrayList<Tweet> readFile();

    public void setCSVFileName(String CSVFileName) {
        this.CSVFileName = CSVFileName;
    }
    /**
     * method for reading the csv state file
      that will be inherited by sub classes
     * @return treemap of states
     */
    public TreeMap<String, State> readCSVFile() {
        try {
            TreeMap<String, State> treeMap = new TreeMap<>();
            FileReader fr = new FileReader(this.CSVFileName);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                String[] lines = line.trim().split(",");
                Double latitude = Double.parseDouble(lines[1]);
                Double longitude = Double.parseDouble(lines[2]);

                State state = new State(lines[0], latitude, longitude);
                treeMap.put(lines[0], state);

            }
            fr.close();
            br.close();
            return treeMap;
        } catch (IOException e) {
            System.err.println("CSV file cannot be read.");
        }
        return null;
    }
}
