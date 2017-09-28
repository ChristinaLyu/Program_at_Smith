
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package nfatoregex;

/**
 *
 * @author ChristinaLyu
 */
public class NFAtoRegex {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) throws FileNotFoundException {
        // TODO code application logic here
        String filename = args[0];
        Scanner reader = new Scanner(new InputStreamReader(System.in));
        BufferedReader file = new BufferedReader(new FileReader(filename));
        ArrayList<String> arrows = new ArrayList<String> ();
        ArrayList<String> states = new ArrayList<String> ();
        String line;
        ArrayList<String> s = new ArrayList<String> ();
        ArrayList<String> reg = new ArrayList<String> ();
        ArrayList<String> e = new ArrayList<String> ();
        String star = "";
        String endd = "";
        String regg = "";
        String change = "";
        String rege = "";
        String changee = "";
        String changes = "";
        int n = 1000000000;
        int j = 1000000000;
        int l = 1000000000;
        try {
            //The first section
            while ((line = file.readLine()) != null){
                arrows.add(line);
                
            }
            String first = arrows.get(0);
            String[] two = first.split(" ");
            String startState = two[0];
            String acceptState = two[1];
            System.out.println("First get the input start state and accepting state: "
                        + startState + " and " + acceptState);
            arrows.remove(0);
            System.out.println("This is the original NFA");
            for (String stat: arrows){
                String[] parts = stat.split(" ");
                String start = parts[0];
                String regex = parts[1];
                String end = parts[2];
                s.add(start);
                reg.add(regex);
                e.add(end);
                star = star + start + " ";
                endd = endd + end;
                System.out.println(stat);
                if (!states.contains(end)){
                    states.add(end);
                }
                if (!states.contains(start)){
                    states.add(start);
                }
            }
            System.out.println("The start states are " + s);
            System.out.println("The end states are " + e);
            //The second section
            System.out.println("Start to eliminate the states one by one.");
            for (String state: states){
                int aS = arrows.size();
              
                if (!state.equals(startState) && !state.equals(acceptState)){
                String regs = "";
                System.out.println("working on State " + state);
                for (int i = 0; i < aS; i ++){
                    String stat = arrows.get(i);
                    
                    String[] strings = stat.split(" ");
                    String starting = strings[0];
                    String ending = strings[2];
                    if (starting.equals(state) 
                        && ending.equals(state)){
                        
                        regs = reg.get(i);
                        regs = "(" + regs + ")" + "*";
                        l = i;
                        
                    }
                }
                
                
            
                if (l != 1000000000){
                    System.out.println("The regex for arc going from State " + state + " to itself changes from "
                            + reg.get(l) + " to " + regs);
                    arrows.remove(l);
                //states.remove(n);
                    s.remove(l);
                    reg.remove(l);
                    e.remove(l);
                    star = star.substring(0, l) + star.substring(l + 1);
                    endd = endd.substring(0, l) + endd.substring(l + 1);
                }
                
                ArrayList<Integer> mint = new ArrayList<Integer> ();
                ArrayList<Integer> hint = new ArrayList<Integer> ();
                ArrayList<String> newRegL = new ArrayList<String> ();
            
                String newString;
                for (int m = 0; m < aS; m++){
                    
                    if (e.get(m).equals(state)){
                        
                        changee = reg.get(m);
                        String startst = s.get(m);
                    
                        for (int h = 0; h < aS; h ++){
                            if (s.get(h).equals(state)){
                           
                                changes = reg.get(h);
                                String endst = e.get(h);
                                
                                String newReg = changee + regs + changes;
                                System.out.println("The new arc from State " + startst + 
                                        " to State " + " has regex of " + newReg);
                                newString = startst + " " + newReg + " " + endst;
                                arrows.add(newString);
                                reg.add(newReg);
                                s.add(startst);
                                e.add(endst);
                            }
                            
                        }
                        
                    }
                    
                }
               
                while (s.contains(state)){
                    int u = s.indexOf(state);
                    arrows.remove(u);
                    reg.remove(u);
                    e.remove(u);
                    s.remove(u);
                    
                }
                while (e.contains(state)){
                    int u = e.indexOf(state);
                    arrows.remove(u);
                    reg.remove(u);
                    e.remove(u);
                    s.remove(u);
                    
                }
                System.out.println("The new NFA is: ");
                for (String arrow: arrows){
                    System.out.println(arrow);
                }   
                }
            }
           
            System.out.println("After removing all other states, start to remove the " +
                    "recurring arcs from the start and accept state.");
            for (int i = 0; i < arrows.size(); i ++){
                String stat = arrows.get(i);
                String[] strings = stat.split(" ");
                String starting = strings[0];
                String ending = strings[2];
                if (starting.equals(startState) 
                        && ending.equals(startState)){
                    regg = reg.get(i);
                    regg = "(" + regg + ")" + "*";
                    
                    j = i;
                    
                }
            }
            //System.out.println("second for loop");
            if (j != 1000000000){
                arrows.remove(j);
                //states.remove(j);
                s.remove(j);
                reg.remove(j);
                e.remove(j);
                for (String arrow: arrows){
                    System.out.println(arrow);
                }
                star = star.substring(0, j) + star.substring(j + 1);
                endd = endd.substring(0, j) + endd.substring(j + 1);
            }
            System.out.println("for the start state");
            for (int m = 0; m < s.size(); m++){
                
                change = reg.get(m);
                if (s.get(m).equals(startState)){
                    System.out.println("change = " + change + "s = " + s);
                    String en = e.get(m);
                    if (!regg.equals("")){
                        reg.remove(m);
                        change = regg + change;
                        //System.out.println("change = " + change);
                        reg.add(m, change);
                        arrows.set(m, startState + " " + change + " " + en);
                        System.out.println("The revised arc connecting State "
                                + startState + " and State " + en + " has regex of " 
                                + change);
                    }
                }
                
            }
            
            for (int i = 0; i < arrows.size(); i ++){
                String stat = arrows.get(i);
                String[] strings = stat.split(" ");
                //System.out.println("split state: " + strings[1]);
                String starting = strings[0];
                String ending = strings[2];
                if (starting.equals(acceptState) 
                        && ending.equals(acceptState)){
                    rege = reg.get(i);
                    rege = "(" + rege + ")" + "*";
                    
                    n = i;
                   
                    
                }
            }
           
            if (n != 1000000000){
                arrows.remove(n);
                //states.remove(n);
                s.remove(n);
                reg.remove(n);
                e.remove(n);
                star = star.substring(0, n) + star.substring(n + 1);
                endd = endd.substring(0, n) + endd.substring(n + 1);
            }
            System.out.println("for accepting state");
            for (int m = 0; m < s.size(); m++){
                if (e.get(m).equals(acceptState)){
                    changee = reg.get(m);
                    String st = s.get(m);
                    if (!rege.equals("")){
                        reg.remove(m);
                        changee = changee + rege;
                        
                        
                        reg.add(m, changee);
                        arrows.set(m, st + " " + changee + " " + acceptState);
                        System.out.println("The revised arc connecting State "
                                + st + " and State " + acceptState + " has regex of " 
                                + change);
                    }
                }
            }
            System.out.println("The final NFA is: ");
            for (String arrow: arrows){
                System.out.println(arrow);
            }
            String regggex = "";
            String reggex = "";
            for (int o = 0; o < arrows.size(); o ++){
                if (s.get(o).equals(startState) && e.get(o).equals(acceptState)){
                   
                    regggex = regggex + reg.get(o) + "U";
                }
                if (s.get(o).equals(acceptState) && e.get(o).equals(startState)){
                    reggex = reggex + reg.get(o) + "U";
                }
            }
            if (!regggex.equals("")){
                regggex = regggex.substring(0, regggex.length()-1);
            }
            if (!reggex.equals("")){
            reggex = reggex.substring(0, reggex.length()-1);
            
                regggex = regggex + "((" + reggex +")(" +  regggex + "))*";
            }
            System.out.println("regex = " + regggex);
        } catch (IOException ex) {
            Logger.getLogger(NFAtoRegex.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
