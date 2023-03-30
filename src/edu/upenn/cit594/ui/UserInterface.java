package edu.upenn.cit594.ui;

import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.processor.Processor;

import java.util.TreeMap;

public class UserInterface {
    private Processor processor;

    public UserInterface(Processor processor) {
        this.processor = processor;
    }


    /**
     * Simple method for displaying our output in the console
     * @param fileName name of file for reading
     */
    public void presentData(String fileName) {
       TreeMap<String,Integer> stateTweetCount = processor.processFileData(fileName);
        for(String s : stateTweetCount.keySet()) {
            System.out.println(s + ": " + stateTweetCount.get(s));
        }
    }

}
