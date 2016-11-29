package com.company;

import java.util.ArrayList;

/**
 * Created by Thomas on 26/11/2016.
 */
public class Rule {
    String antecedent;
    String consequent;

    Rule(String antecedent, String consequent){
        this.antecedent = antecedent;
        this.consequent = consequent;
    }

    public String getConsequent() {
        return consequent;
    }

    public void setConsequent(String consequent) {
        this.consequent = consequent;
    }

    public String getAntecedent() {
        return antecedent;
    }

    public void setAntecedent(String antecedent) {
        this.antecedent = antecedent;
    }

    public boolean antecedentMatches(WorkingMemory wm){
        //if the antecedent matches the item passed in return true
        //
        /*System.out.print("Attempting to match ");
        for(String g: wm.memory){ System.out.print(g + " "); }
        System.out.println("to " + antecedent);
        */
        //for now an antecedent attempts to match everything within brackets to the rulebase and ignores operators as only and is given

        //for each item in the antecedent, see if it matches a goal
        //return false if any are missing


        for(String g : getItems()){
            boolean itemMatched = false;
            for(String memItem : wm.memory) {
                if (memItem.equals(g)) {
                    itemMatched = true;
                }
            }
            if(!itemMatched){
                return false;
            }
        }

        return true;
    }

    public boolean consequentMatches(String goal){
        //checks if the consequent matches the items in the working memory
        //returns true if it does
        //System.out.println("Attempting to match " + goal + " to " + consequent);


        //for now consequent is only a single item so we just take the item from between the brackets and match it to every string in the wm
        return (consequent.equals(goal));



    }

    public ArrayList<String> getItems(){
        //System.out.println("Hello");

        ArrayList<String> items = new ArrayList<>();
        //returns an arraylist of every item in the antecedent
        //for each character in the antecedent
        int bracketCount = 0;
        int prevBC = Integer.MAX_VALUE;

        String tempItem = "";
        boolean start = true;
        char[] ante = antecedent.toCharArray();

        for(char c : ante) {
            if(c == '('){
                bracketCount ++;
            }else if(c == ')'){
                bracketCount --;
            }

            if (bracketCount != 0 ) {
                tempItem += c;
            }else if(prevBC != bracketCount){

                tempItem += c;
                items.add(tempItem);
                tempItem = "";

            }
            prevBC = bracketCount;

        }
        /*
        System.out.print("Items found in antecedent: ");
        for(String item : items){
            System.out.print(item + " ");
        }
        System.out.println();
        */

        return items;
    }
}
