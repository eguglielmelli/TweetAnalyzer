package edu.upenn.cit594.datamanagement;
import edu.upenn.cit594.util.Tweet;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class textFileReader extends fileReader{

    private String fileName;

    /**
     * Constructor with filename passed as parameter
     * @param fileName
     */
    public textFileReader(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Inherited method for reading the text file
     * @return arraylist of tweets
     */
    @Override
    public ArrayList<Tweet> readFile() {
        try {
            ArrayList<Tweet> tweetList = new ArrayList<>();
            FileReader fr = new FileReader(this.fileName);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while((line = br.readLine())!= null) {
                String[] lines = line.split("\t");

                //parse the lat/longitude and remove the "[]"
                String location = lines[0].substring(1,lines[0].length()-1);
                String[] array = location.split(",");
                double latitude = Double.parseDouble(array[0]);
                double longitude = Double.parseDouble(array[1]);

                Tweet tweet = new Tweet(latitude,longitude,lines[lines.length-1].toLowerCase(Locale.ROOT));
                tweetList.add(tweet);
            }
            fr.close();
            br.close();
            return tweetList;
        }
        catch (IOException e){
            e.printStackTrace();

        }
        return null;
    }
}
