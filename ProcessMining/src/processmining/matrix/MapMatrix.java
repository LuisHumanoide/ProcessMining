/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processmining.matrix;

import java.util.HashMap;
import java.util.LinkedList;
import processmining.Config;
import processmining.Graph.Graph;
import processmining.Graph.Sequence;
import processmining.Graph.Vertex;

/**
 * class that represent the incidence matrix
 *
 * @author Humanoide
 */
public class MapMatrix {

    /*array of rows, for example, first we acces to the column and after to the cell*/
    public HashMap<String, TokenNames> Rows;

    /*binary matrix*/
    int[][] intMatrix;

    public MapMatrix() {
        Rows = new HashMap<>();
    }

    /**
     * construct the initial matrix by the sequences
     *
     * @param sequences
     */
    public void makeMatrix(LinkedList<Sequence> sequences) {
        /*
        initialize the matrix putting 0 in all cells
         */
        for (int i = 0; i < LabelNames.labelNamesList().size(); i++) {
            TokenNames tn = new TokenNames();
            Rows.put(LabelNames.labelNamesList().get(i), tn);
        }
        /*
        make the matrix by the sequence
         */
        for (Sequence sq : sequences) {
            for (int i = 0; i < sq.sequence.size() - 1; i++) {
                TokenNames tn = Rows.get(sq.sequence.get(i));
                tn.addOccurrence(sq.sequence.get(i + 1));
            }
        }

    }
    /**
     * create a mapMatrix by a graph 
     * @param graph 
     */
    public HashMap<String, TokenNames> makeMatrix(Graph graph){
         /*
        initialize the matrix putting 0 in all cells
         */
        for (int i = 0; i < LabelNames.labelNamesList().size(); i++) {
            TokenNames tn = new TokenNames();
            Rows.put(LabelNames.labelNamesList().get(i), tn);
        }
        /*
         * for each node and each child of the node, the matrix will be formed
         */
        /*
        for(Vertex v:graph.getNodes()){
            TokenNames tn=Rows.get(v.getLabel());
            for(Vertex childs:v.getDescendants().values()){
                tn.addOccurrence(childs.getLabel());
            }
        }*/
        
        for(Vertex v:graph.getNodes()){
            TokenNames tn=Rows.get(v.getLabel());
            for(Vertex childs:v.getChilds()){
                tn.addOccurrence(childs.getLabel());
            }
        }
        printMatrix();
        return Rows;
    }

    /**
     * print the map matrix
     */
    public void printMatrix() {
        if (Config.canPrint) {
            String mm = "";

            for (int i = 0; i < LabelNames.labelNamesList().size(); i++) {

                TokenNames tn = Rows.get(LabelNames.labelNamesList().get(i));
                mm = mm + LabelNames.labelNamesList().get(i) + "\t";

                for (int j = 0; j < LabelNames.labelNamesList().size(); j++) {
                    mm = mm + "[" + LabelNames.labelNamesList().get(j) + "," + tn.TokenMap.get(LabelNames.labelNamesList().get(j)) + "]" + " ";
                }
                mm = mm + "\n";
            }

            System.out.println(mm);
        }
    }

}

/**
 * this class represent one row
 *
 * @author Humanoide
 */
class TokenNames {

    HashMap<String, Integer> TokenMap;

    public TokenNames() {
        TokenMap = new HashMap<String, Integer>();
        for (String name : LabelNames.labelNames) {
            TokenMap.put(name, 0);
        }
    }

    public void addOccurrence(String name) {
        TokenMap.replace(name, TokenMap.get(name) + 1);
    }
}
