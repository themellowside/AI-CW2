package com.company;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Thomas on 25/11/2016.
 */
public class WorkingMemory {
    ArrayList<String> memory;

    WorkingMemory(String fileName){
        try {
            System.out.println("Loading goals...");
            memory = extractGoals(fileName);
        }catch(Throwable e){
            System.out.println(e);
        }

        System.out.println("Printing working memory: ");
        for(String m : memory) {
            System.out.print(m + " ");
        }
        System.out.println();

    }

    private ArrayList<String> extractGoals(String fileName) throws Throwable {
        //parses rulebase text file into arraylist of string arrays containing number of rules
        ArrayList<String> memory = new ArrayList<>();
        File input = new File(fileName);
        FileReader fileReader = new FileReader(input);
        int bracketCounter = 0;
        String currentItem = "";

        while(fileReader.ready()) {
            char c = (char) fileReader.read();
            if(c == '('){
                currentItem = "" + c;
                bracketCounter ++;
            }else if(c == ')'){
                currentItem += c;
                memory.add(currentItem);
                bracketCounter --;
            }else if(c == ' '){
                //ignore whitespace
            }else{
                currentItem = currentItem +  c;
            }
        }

        if(bracketCounter != 0){
            throw new Throwable("Finished reading file with uneven text wrapping");
        }
        return memory;
    }

    public boolean containsGoal(String goal){
        return memory.contains(goal);
    }

    public boolean addMemory(String goal){
        if(memory.contains(goal)){ //no point returning goals?
            return false;
        }else{
            memory.add(goal);
            return true;
        }
    }

    public boolean removeMemory(String goal){
        return memory.remove(goal);
    }

    public ArrayList<String> getMemory() {
        return memory;
    }

    public void setMemory(ArrayList<String> memory) {
        this.memory = memory;
    }

    @Override
    public String toString() {
        String str = "";
        for(String s: memory){
            str += s + " ";
        }
        return str;
    }
}
