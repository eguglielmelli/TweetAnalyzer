package edu.upenn.cit594.processor;
import edu.upenn.cit594.datamanagement.fileReader;
import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.util.State;
import edu.upenn.cit594.util.Tweet;

import java.util.*;

public class Processor {
    private fileReader reader;

    public Processor(fileReader reader) {
        this.reader = reader;

    }

    /**
     * Find the tweets from the master list that are within our "flu" parameters
     * @param tweets list of tweets
     * @return linkedhashset of tweets
     */
    public LinkedHashSet<Tweet> fluTweetFinder(ArrayList<Tweet> tweets) {
        if(tweets == null) {
            return null;
        }
        LinkedHashSet<Tweet> setOfFluTweets = new LinkedHashSet<>();
        //regex pattern checks for non-letter after "u" using a look ahead and then
        //accepts other characters thereafter
        String pattern = "^(?:#)?flu(?![a-z])(?:\\S*)$";
        for (Tweet tweet : tweets) {
            String[] array = tweet.getMessage().trim().split(" ");
            for (String s : array) {
                if (s.matches(pattern)) {
                    setOfFluTweets.add(tweet);
                }
            }
        }
        return setOfFluTweets;
    }

    /**
     * Helper method attempts to match the latitude and longitude of a given tweet with the
     closest state
     * @param latitude of tweet
     * @param longitude of tweet
     * @param states treemap of state objects
     * @return closest state string
     */
    public String findClosestState(Double latitude,Double longitude,TreeMap<String,State> states) {
        if(latitude == null || longitude == null || states == null) {
            return null;
        }
        double minDistance = Double.MAX_VALUE;
        String stateofFluTweet = "";
        for(String s : states.keySet()) {
            double distance = Math.sqrt(Math.pow(states.get(s).getLongitude()-longitude,2) + Math.pow(states.get(s).getLatitude()-latitude,2));
            if(distance < minDistance) {
                minDistance = distance;
                stateofFluTweet = s;
            }
        }
        return stateofFluTweet;
    }

    /**
     * This is the main method for this class which includes the actual logging of states and messages, and
     our output of to the console
     * @return treemap of output
     */
    public TreeMap<String,Integer> processFileData() {
        ArrayList<Tweet> list = reader.readFile();
        LinkedHashSet<Tweet> setOfFluTweets = fluTweetFinder(list);
        TreeMap<String,State> stateTreeMap = reader.readCSVFile();
        //call our logger as we are going to log tweets once we match states
        Logger logger = Logger.getInstance();

        TreeMap<String, Integer> stateTweetCount = new TreeMap<>();
        for (Tweet tweet : setOfFluTweets) {
            //call our helper method for closest state, and as soon as we find it send it to the logger
            //for the txt file output
            String closestState = findClosestState(tweet.getLatitude(), tweet.getLongitude(), stateTreeMap);
            logger.log(closestState + "\t" + tweet.getMessage());

            //this data structure is for console output, we insert them into the treemap so the states are ordered
            if (!stateTweetCount.containsKey(closestState)) {
                stateTweetCount.put(closestState, 1);
            } else {
                stateTweetCount.replace(closestState, stateTweetCount.get(closestState) + 1);
            }
        }
        return stateTweetCount;
    }
}
