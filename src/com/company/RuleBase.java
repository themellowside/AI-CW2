package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Thomas on 25/11/2016.
 */
public class RuleBase {

    ArrayList<Rule> rules = new ArrayList<>();

    RuleBase(String fileName){

        try {
            System.out.println("Loading rulebase...");
            rules = extractRuleBase(fileName);
        }catch(IOException e){
            System.out.println(e);
        }
        System.out.println("Printing rulebase:");
        for(Rule rule : rules){
            System.out.println("IF " + rule.getAntecedent() + " THEN " + rule.getConsequent());
        }
        System.out.println();

    }

    private ArrayList<Rule> extractRuleBase(String fileName) throws IOException{
        //parses rulebase text file into arraylist of string arrays containing number of rules
        ArrayList<String> rules = new ArrayList<>();
        ArrayList<Rule> splitRules = new ArrayList<>();
        File input = new File(fileName);
        FileReader fileReader = new FileReader(input);
        int bracketCount = 0;
        String temp = "";
        while(fileReader.ready()) {


            char current = (char) fileReader.read();
            //System.out.print(current);
            if(current == '('){
                bracketCount ++;
            }else if(current== ')'){
                bracketCount --;
                if(bracketCount == 0){
                    if(temp.length() > 0) {
                        rules.add(temp);
                    }
                    temp = "";
                }

            }
            if(current != ' ') {
                temp = temp + current;
            }

        }

        for(String rule: rules){
            char[] ruleChars = rule.toCharArray();
            //iterate to the IF
            //then until it hits a bracket it can't account for record the data
            String antecedent = "";
            String consequent = "";
            for(int i = 3; i < ruleChars.length; i++){

                if(ruleChars[i-1] == 'I' && ruleChars[i] == 'F'){
                    antecedent = getSubSection(ruleChars, i+1);
                    i += antecedent.length();
                    //System.out.print(antecedent);
                }
                if(ruleChars[i-3] == 'T' && ruleChars[i-2] == 'H' && ruleChars[i-1] == 'E' && ruleChars[i] == 'N'){
                    consequent = getSubSection(ruleChars, i+1);

                    //System.out.println(consequent);
                    break;
                }
            }
            splitRules.add(new Rule(antecedent, consequent));

        }

        //chararray of rules now

        return splitRules;
    }

    private String getSubSection(char[] chars, int j){
        //returns the contents of a char array from starting index until it meets an equal amount of closing brackets
        int bracketCount = 0;
        String temp = "";
        for(int i = j; i < chars.length; i ++){
            char current = chars[i];
            if(current == '('){
                bracketCount ++;

            }else if(current== ')'){
                bracketCount --;
                if(bracketCount == -1){
                    break;
                }
            }
            temp += current;

        }
        return temp;

    }

    public ArrayList<Rule> getRules() {
        return rules;
    }
}
