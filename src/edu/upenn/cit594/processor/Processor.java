package edu.upenn.cit594.processor;
import edu.upenn.cit594.datamanagement.fileReader;
import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.util.State;
import edu.upenn.cit594.util.Tweet;

import java.util.*;

public class Processor {
    private fileReader reader;

    public Processor() {

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
        String closestState = "";
        for(String s : states.keySet()) {
            double distance = Math.sqrt(Math.pow(states.get(s).getLongitude()-longitude,2) + Math.pow(states.get(s).getLatitude()-latitude,2));
            if(distance < minDistance) {
                minDistance = distance;
                closestState = s;
            }
        }
        return closestState;
    }

    /**
     * This is the main method for this class which includes the actual logging of states and messages, and
     our output of to the console
     * @param fileName name of input file that we log to
     * @param map treemap of states
     * @param setOfFluTweets the sets of "flu" tweets
     * @return treemap of output
     */
    public TreeMap<String,Integer> processFileData(String fileName,TreeMap<String,State> map, LinkedHashSet<Tweet> setOfFluTweets) {
        if(fileName == null || map == null || setOfFluTweets == null) {
            return null;
        }
        //create the first and only instance of logger in the program
        Logger logger = Logger.getInstance();
        logger.setOrChangeDestination(fileName);

        TreeMap<String, Integer> stateTweetCount = new TreeMap<>();
        for (Tweet tweet : setOfFluTweets) {
            //call our helper method for closest state, and as soon as we find it send it to the logger
            //for the txt file output
            String closestState = findClosestState(tweet.getLatitude(), tweet.getLongitude(), map);
            logger.log(closestState + ":" + "\t" + tweet.getMessage());

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