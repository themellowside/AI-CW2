package com.company;

import java.util.ArrayList;

/**
 * Created by Thomas on 25/11/2016.
 */
public class InferenceEngine {

    //key terms
    //backward chaining:
    //      attempts to match goals to consequents of a rule base to see if there is data available
    //
    //rule:
    //      a statement consisting of an antecedent and a consequent
    //
    //goal:
    //      a label which is being searched for
    //
    //antecedent:
    //      the first half of a rule, the "if" clause. (e.g. "if (ab) & (cd)")
    //
    //consequent:
    //      the second half of a rule, the "then" clause. (e.g. "then (ef)")
    //
    //working memory:
    //      a list of goals which need to be confirmed.
    //
    //rulebase:
    //      a list of rules from which a goal is searched for
    //
    //
    //input: list of goals, list of rules
    //search inference rules until it finds a rule with a cosequent matching the desired goal
    //  if the antecedent of that rule is not known to be true
    //      add it to the list of goals
    //
    RuleBase ruleBase;
    WorkingMemory wm;

    InferenceEngine(String rules, String memory){
        ruleBase = new RuleBase(rules);
        wm = new WorkingMemory(memory);
    }

    void recursiveInferenceAlgorithm(String goal, String tabIndent){
        ArrayList<Rule> rules = ruleBase.getRules();
        //for each rule
        //System.out.println(wm.memory);
        for(Rule rule : rules) {
            //check for a consequent that matches the goal
            if(rule.consequentMatches(goal)) {
                System.out.println(tabIndent + "Matching consequent for goal " + goal + " found in rule " + rule.getAntecedent() + ": " + rule.getConsequent());
                //if the antecedent is valid given the working memory
                if(rule.antecedentMatches(wm)) {
                    System.out.println(tabIndent + "Goal " + goal +" has been met");
                    wm.addMemory(goal);
                    System.out.println(tabIndent + "Working memory is now: " + wm.toString());
                    break;
                }else{
                    System.out.println(tabIndent+ "Goal " + goal + " not met. Searching through subgoals...");
                    //if the antecedent isn't in the working memory
                    ArrayList<String> subGoals = rule.getItems();

                    for(String subGoal : subGoals){
                        if(!wm.containsGoal(subGoal)) {
                            System.out.println(tabIndent + "Searching for subgoal: " + subGoal);
                            recursiveInferenceAlgorithm(subGoal, tabIndent + "    ");
                        }
                    }
                    //check again to see if the antecedent matches after searching for all the subgoals
                    if(rule.antecedentMatches(wm)) {
                        System.out.println(tabIndent + "Goal " + goal + " has been met");
                        wm.addMemory(goal);
                        System.out.println(tabIndent + "Working memory is now: " + wm.toString());
                        break;
                    }

                    //break;
                    //recursiveInferenceAlgorithm with each new antecedent goal in mind
                    //recursiveInferenceAlgorithm();
                }

            }else{
                System.out.println(tabIndent + "Matching Consequent for goal " + goal + " not found in rule " + rule.getAntecedent() +": " + rule.getConsequent());
            }
        }

    }

}
