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
     */
    public void presentData() {
       TreeMap<String,Integer> stateTweetCount = processor.processFileData();
        for(String s : stateTweetCount.keySet()) {
            System.out.println(s + ": " + stateTweetCount.get(s));
        }
    }

}
