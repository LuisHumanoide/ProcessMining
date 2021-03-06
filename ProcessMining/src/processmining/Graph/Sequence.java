/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processmining.Graph;

import java.util.HashSet;
import java.util.LinkedList;
import processmining.Config;
import processmining.matrix.LabelNames;

/**
 *
 * @author Humanoide
 */
public class Sequence {
    /*the sequence*/
    public LinkedList<String> sequence;
    /*registry of the names*/
    HashSet<String> nodeNames;
    /*registry of the label structure*/
    HashSet<Label> labels;
    /**
     * Constructor
     */
    public Sequence() {
        nodeNames=new HashSet<String>();
        sequence=new LinkedList<String>();
        labels=new HashSet<Label>();
    }
    /**
     * add a node or name to the sequence
     * @param label 
     */
    public void addNode(String label){
        /*if the name is new*/
        if(nodeNames.add(label)){
            sequence.add(label);
            labels.add(new Label(label));
            //this name is added to static variable for using in the matrix
            LabelNames.addLabelNames(label);
        }
        /*if is repeated*/
        else{
            Label lb=findLabel(label);
            lb.setCounter(lb.getCounter()+1);
            sequence.add(label+lb.getCounter());
            //this name is added to static variable for using in the matrix
            LabelNames.addLabelNames(label+lb.getCounter());//This is were the "V'" are generated. A gives rise to A', and so on.
        }
        
    }
    /*find label by the string name*/
    public Label findLabel(String name){
        for(Label lb:labels){
            if(name.equals(lb.getLabel())){
                return lb;
            }
        }
        return null;
    }
    /*print the sequence*/
    public void printSequence(){
        if (Config.canPrint)
        for(String s:sequence){
            System.out.print(s+" ");
        }
    }
    
    
}
