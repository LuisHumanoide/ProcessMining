/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processmining.matrix;

import java.util.HashMap;
import java.util.HashSet;
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
     * 31/July/2017 AUGC: Added functionality to compute the "Following" relation, as described by the Powerpoint presentation.
     * @param sequences
     */
    public void makeMatrix(LinkedList<Sequence> sequences) {
        
        //This hash maps help to know which transitions happen before and after each transition.
        HashMap< String, HashSet<String> > HappenedBefore = new HashMap<>();
        HashMap< String, HashSet<String> > HappenedAfter = new HashMap<>();
        /*
        initialize the matrix putting 0 in all cells
         */
        for (int i = 0; i < LabelNames.labelNamesList().size(); i++) {
            TokenNames tn = new TokenNames();
            Rows.put(LabelNames.labelNamesList().get(i), tn);
            HappenedBefore.put(LabelNames.labelNamesList().get(i), new HashSet<>());
            HappenedAfter.put(LabelNames.labelNamesList().get(i), new HashSet<>());
        }

        /*
        make the matrix by the sequence
         */
        for (Sequence sq : sequences) {
            for (int i = 0; i < sq.sequence.size(); i++) {
                for( int j = i +1 ; j < sq.sequence.size() ; j++ )
                {
                    HappenedAfter.get(sq.sequence.get(i)).add(sq.sequence.get(j));
                }
                for( int k = i -1 ; k >= 0; k-- )
                {
                    HappenedBefore.get(sq.sequence.get(i)).add(sq.sequence.get(k));
                }
                /*This is how parra did it.*/
                //COMMENTED FOR DEBUGGING ONLY!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! 31 JULY 2017
                TokenNames tn = Rows.get(sq.sequence.get(i));
                if(i+1 < sq.sequence.size())
                {
                    tn.addOccurrence(sq.sequence.get(i + 1));//This is where you fill the matrix INCLUDING the V' vertices.
                }
            }
        }

        //In here, remove the ones that appear independent of each other.
        //Also print the values for debugging purposes.
        for (int i = 0; i < LabelNames.labelNamesList().size(); i++) 
        {
            String ActualLabel = LabelNames.labelNamesList().get(i);
            HashSet<String> hsTemp = new HashSet<>();
            hsTemp.addAll( HappenedAfter.get(ActualLabel) );
            HappenedAfter.get(ActualLabel).removeAll( HappenedBefore.get(ActualLabel) );
            HappenedBefore.get(ActualLabel).removeAll( hsTemp);
            
            
            //DEBUG: Print the ones that happened before.
            System.out.println (" The transitions that happen Before : " + ActualLabel + " are: ");
            for( String szName :  HappenedBefore.get(ActualLabel) )
            {
                System.out.print(szName  +" ");
            }
            System.out.println ("");
            
            //NOTE: The ones of the "happened before" relation are not trimmed of the ones that are also in the "Happened after"
            //NOTE: This has been corrected, please check so the Before-relation is also trimmed.
            System.out.println (" The transitions that happen After : " + ActualLabel + "  are: ");
            for( String szName :  HappenedAfter.get(ActualLabel) )
            {
                System.out.print(szName  +" ");
            }
            System.out.println ("");
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
            for(Vertex VDes:v.getDescendants().values()){
                tn.addOccurrence(VDes.getLabel());
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
