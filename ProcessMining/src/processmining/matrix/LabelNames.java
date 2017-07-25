/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processmining.matrix;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 *in this class, any class can access to the list of node names
 * @author Humanoide
 */
public class LabelNames {
    
    static TreeSet<String> labelNames=new TreeSet<>();

    public static void addLabelNames(String name){
        labelNames.add(name);
    }
    
    public static ArrayList<String> labelNamesList(){
        ArrayList<String> list=new ArrayList<String>();
        list.addAll(labelNames);
        return list;
    }
    
    public static String get(int index){
        return labelNamesList().get(index);
    }
    
    
}
