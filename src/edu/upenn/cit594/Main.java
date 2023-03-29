package edu.upenn.cit594;

import edu.upenn.cit594.datamanagement.JsonFileReader;
import edu.upenn.cit594.ui.UserInterface;
import edu.upenn.cit594.util.State;
import edu.upenn.cit594.datamanagement.textFileReader;
import edu.upenn.cit594.processor.Processor;
import edu.upenn.cit594.util.Tweet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) throws IOException{
        if(args.length != 3) {
            System.err.println("Length of args is invalid.");
            return;
        }
        String tweetFile = args[0];
        if(!tweetFile.toLowerCase(Locale.ROOT).endsWith(".txt") && !tweetFile.toLowerCase(Locale.ROOT).endsWith(".json")) {
            System.err.println("Invalid input. The accepted files extensions are: .txt and .json.");
            return;
        }
        File anotherFile = new File(tweetFile);
        if(!anotherFile.exists() || !anotherFile.canRead()) {
            System.err.println("The tweet input file cannot be read or does not exist.");
            return;
        }
        String stateFile = args[1];
        File statesFile = new File(stateFile);
        if(!statesFile.exists() || !statesFile.canRead()) {
            System.err.println("The state input file cannot be read or does not exist.");
            return;
        }
        String logFileName = args[2];
        File logfile = new File(logFileName);
        try {
            if(!logfile.exists()) {
                logfile.createNewFile();
            }
            if(!logfile.canWrite()) {
                System.err.println("The log file cannot be written to.");
                return;
            }
        }
        catch (IOException e){
            System.err.println("The log file cannot be opened or written to.");
            return;
        }

        UserInterface ui = new UserInterface();
        Processor processor = new Processor();
        
        if (args[0].endsWith(".txt")) {
            textFileReader tr = new textFileReader(args[0]);
            TreeMap<String, State> treeMap = tr.readCSVFile(args[1]);
            ArrayList<Tweet> list = tr.readFile();
            LinkedHashSet<Tweet> setOfFluTweets = processor.fluTweetFinder(list);
            TreeMap<String, Integer> stateTweetCount = processor.processFileData(args[2], treeMap, setOfFluTweets);
            ui.presentData(stateTweetCount);
        }
        else if (args[0].endsWith(".json")) {
            JsonFileReader js = new JsonFileReader(args[0]);
            ArrayList<Tweet> list = js.readFile();
            TreeMap<String, State> treeMap = js.readCSVFile(args[1]);
            LinkedHashSet<Tweet> setOfFluTweets = processor.fluTweetFinder(list);
            TreeMap<String, Integer> stateTweetCount = processor.processFileData(args[2], treeMap, setOfFluTweets);
            ui.presentData(stateTweetCount);
        }
    }
}

