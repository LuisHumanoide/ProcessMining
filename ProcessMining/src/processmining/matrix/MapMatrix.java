/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processmining.matrix;

import java.util.HashMap;
import java.util.LinkedList;
import processmining.Graph.Sequence;

/**
 * class that represent the incidence matrix
 *
 * @author Humanoide
 */
public class Matrix {

    HashMap<String, TokenNames> Rows;
    Token[][] tokenMatrix;
    int[][] intMatrix;

    public Matrix() {
        Rows=new HashMap<>();
    }

    public void makeMatrix(LinkedList<Sequence> sequences) {

        for (int i = 0; i < LabelNames.labelNamesList().size(); i++) {
            TokenNames tn = new TokenNames();
            Rows.put(LabelNames.labelNamesList().get(i), tn);
        }

        for(Sequence sq:sequences){
            /*
            for(int i=0;i<sq.sequence.size()-1;i++){
                
            }*/
        }

    }

    public void printMatrix() {
        String mm = "";
        
        for (int i = 0; i < LabelNames.labelNamesList().size(); i++) {
            
            TokenNames tn = Rows.get(LabelNames.labelNamesList().get(i));
            mm = mm+LabelNames.labelNamesList().get(i) + "\t";
            
            for (int j = 0; j < LabelNames.labelNamesList().size(); j++) {
                mm=mm+"["+LabelNames.labelNamesList().get(j)+","+tn.TokenMap.get(LabelNames.labelNamesList().get(j))+"]"+" ";
            }
            mm=mm+"\n";
        }
        
        System.out.println(mm);
    }

}

class TokenNames {

    HashMap<String, Integer> TokenMap;

    public TokenNames() {
        TokenMap = new HashMap<String, Integer>();
        for (String name : LabelNames.labelNames) {
            TokenMap.put(name, 0);
        }
    }

    public void setCell(String name) {

    }
}

class Token {

    String name;
    int occurrences;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOccurrences() {
        return occurrences;
    }

    public void setOccurrences(int occurrences) {
        this.occurrences = occurrences;
    }

    public Token(String name, int occurrences) {
        this.name = name;
        this.occurrences = occurrences;
    }
}
