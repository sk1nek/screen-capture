package me.mjaroszewicz;

import javax.swing.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class Main {

    private static final String[] allowedPreferenceKeys = {"type", "filename","storage"};

    private static HashMap<String, String> preferences = new HashMap<>();
    private static HashSet<String> allowedPreferenceKeySet = new HashSet<>(Arrays.asList(allowedPreferenceKeys));

    //TODO - fix gif creation

    public static void main(String[] args) {

        if (!parseRunArguments(args)) {
            System.err.println("Invalid arguments, please follow specification.");
            System.exit(-1);
        }

        SwingUtilities.invokeLater(UI::new);

    }

    /**
     * @param args program run arguments
     * @return true if provided arguments were valid
     */
    private static boolean parseRunArguments(String[] args){

        for(String s: args){
            if(s.matches("-.*:.*")){
                String[] split = s.substring(1).split(":");

                if(!putPreference(split[0], split[1]))
                    return false;

            }else{
                return false;
            }
        }


        return true;
    }

    private static boolean putPreference(String k, String v){

        if(allowedPreferenceKeySet.contains(k))
            preferences.put(k, v);
        else
            return false;

        return true;
    }

    /**
     * Simple nullpointer-proof preference access method.
     *
     * @param k preference key
     * @return preference value associated with provided key
     */
    public static String getPreference(String k){

        String v = preferences.get(k);

        if(v == null)
            return "";

        else return v;
    }

    public static HashMap<String, String> getPreferences(){
        return preferences;
    }


}
