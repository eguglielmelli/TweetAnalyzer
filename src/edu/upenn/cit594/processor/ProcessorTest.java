package edu.upenn.cit594.processor;

import edu.upenn.cit594.datamanagement.fileReader;
import edu.upenn.cit594.datamanagement.textFileReader;
import edu.upenn.cit594.util.State;
import edu.upenn.cit594.util.Tweet;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class ProcessorTest {

    @org.junit.jupiter.api.Test
    void fluTweetFinder() {
        Processor processor = new Processor();
        textFileReader tr = new textFileReader("flu_tweets.txt");
        ArrayList<Tweet> list = tr.readFile();
        LinkedHashSet<Tweet> tweetList = processor.fluTweetFinder(list);
        assertTrue(tweetList.size() == 14);

    }

    @org.junit.jupiter.api.Test
    void findClosestState() {
        Processor processor = new Processor();
        fileReader fileReader = new textFileReader("flu_tweets.txt");
        TreeMap<String, State> states = fileReader.readCSVFile("states.csv");
        double latitude = 27.554639479999999;
        double longitude =  -99.506350139999995;
        String closestState = processor.findClosestState(latitude,longitude,states);
        assertTrue(closestState.equals("Texas"));

        latitude = 38.448899;
        longitude = -109.690973;
        closestState = processor.findClosestState(latitude,longitude,states);
        assertTrue(closestState.equals("Utah"));


        latitude = 35.912208;
        longitude = -99.718105;
        closestState = processor.findClosestState(latitude,longitude,states);
        assertTrue(closestState.equals("Oklahoma"));


        latitude = 33.268193;
        longitude = -94.477051;
        closestState = processor.findClosestState(latitude,longitude,states);
        assertTrue(closestState.equals("Arkansas"));

        latitude = 30.830977;
        longitude = -87.485815;
        closestState = processor.findClosestState(latitude,longitude,states);
        assertTrue(closestState.equals("Alabama"));









    }

    @org.junit.jupiter.api.Test
    void processFileData() {
    }
}