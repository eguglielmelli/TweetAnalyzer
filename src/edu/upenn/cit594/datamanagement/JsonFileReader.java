package edu.upenn.cit594.datamanagement;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import edu.upenn.cit594.util.Tweet;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class JsonFileReader extends fileReader {

    private String fileName;

    /**
     * Constructor that creates the json file reader
     * @param fileName
     */
    public JsonFileReader(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Inherited method that reads the json file into an arraylist of tweets
     * @return arraylist of tweets
     */
    @Override
    public ArrayList<Tweet> readFile() {
        ArrayList<Tweet> tweetList = new ArrayList<>();
        try (FileReader reader = new FileReader(this.fileName)) {
            JSONParser json = new JSONParser();
            JSONObject jsonObject;
            JSONArray array;
            array = (JSONArray) json.parse(reader);
            //iterate over our Json array and do the parsing of data
            for(Object jsObject : array) {
              jsonObject = (JSONObject) jsObject;
              //use .substring() to eliminate the "[]" in latitude and longitude, then split based on ","
              String location = jsonObject.get("location").toString().substring(1,jsonObject.get("location").toString().length()-1);
              String[] locationArray = location.split(",");
              double latitude = Double.parseDouble(locationArray[0]);
              double longitude = Double.parseDouble(locationArray[1]);

              String tweetMessage = jsonObject.get("text").toString();
              Tweet tweet = new Tweet(latitude,longitude,tweetMessage.toLowerCase(Locale.ROOT));
              tweetList.add(tweet);
           }
           return tweetList;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            System.err.println(this.fileName + " could not be parsed.");
            e.printStackTrace();
        }
        return null;
    }
}
