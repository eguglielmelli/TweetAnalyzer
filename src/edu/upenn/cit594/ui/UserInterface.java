package edu.upenn.cit594.ui;

import java.util.TreeMap;

public class UserInterface {

    /**
     * Simple method for displaying our output in the console
     * @param stateTweetCount map of results with state name and count
     */
    public void presentData(TreeMap<String,Integer> stateTweetCount) {
        for(String s : stateTweetCount.keySet()) {
            System.out.println(s + ": " + stateTweetCount.get(s));
        }
    }

}
