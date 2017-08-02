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
        HashMap< String, HashSet<String> > Independents = new HashMap<>();
       
        /*
        initialize the matrix putting 0 in all cells
         */
        for (int i = 0; i < LabelNames.labelNamesList().size(); i++) {
            TokenNames tn = new TokenNames();
            Rows.put(LabelNames.labelNamesList().get(i), tn);
            HappenedBefore.put(LabelNames.labelNamesList().get(i), new HashSet<>());
            HappenedAfter.put(LabelNames.labelNamesList().get(i), new HashSet<>());
            Independents.put(LabelNames.labelNamesList().get(i), new HashSet<>());
        }

        /*
        make the matrix by the sequence
         */
        for (Sequence sq : sequences) {
            for (int i = 0; i < sq.sequence.size(); i++) {
                String szActual = sq.sequence.get(i);
                String szAfter ;
                String szBefore; 
                for( int j = i +1 ; j < sq.sequence.size() ; j++ )
                {
                    szAfter = sq.sequence.get(j);//Auxiliar variable to store the name of this Activity.
                    if(HappenedBefore.get(szActual).contains(szAfter)) //Check if it had happened before szActual in another sequence.
                    {
                        //If it has, then:
                        HappenedBefore.get(szActual).remove(szAfter); //Remove it from the "HappenedBefore", as it now has happened after.
                        HappenedAfter.get(szAfter).remove(szActual); //also remove it the other way
                        Independents.get(szActual).add(szAfter); //It is now independent, according to the definition.
                        continue;//Then, continue to next szAfter.
                    }
                    if( Independents.get(szActual).contains(szAfter) ) //If it is already marked as independent.
                    {
                        continue;//Then, continue to next szAfter.
                    }
                    //else, add "szAfter" to the ones that happened after szActual
                    HappenedAfter.get(szActual).add(szAfter);
                    HappenedBefore.get(szAfter).add(szActual);
                }
                for( int k = i -1 ; k >= 0; k-- ) //For each activity found before szActual in this sequence:
                {
                    szBefore = sq.sequence.get(k);//Auxiliar variable to store the name of this Activity.
                    if(HappenedAfter.get(szActual).contains(szBefore))//Check if it had happened after szActual in another sequence.
                    {
                        HappenedAfter.get(szActual).remove(szBefore); //Remove it from the "HappenedAfter", as it now has happened Vefore.
                        HappenedBefore.get(szBefore).remove(szActual); //also remove it the other way
                        Independents.get(szActual).add(szBefore); //It is now independent, according to the definition.
                        continue;//Then, continue to next szBefore.
                    }
                    if( Independents.get(szActual).contains(szBefore) ) //If it is already marked as independent.
                    {
                        continue;//Then, continue to next szAfter.
                    }
                    /*AYUDA*/
                    boolean bFoundInconsistency = false;
                    for( String szFollow : HappenedAfter.get(szActual) )
                    {
                        if(HappenedAfter.get(szFollow).contains(szBefore))
                        {
                            //Then, they cannot be related, they must be independent.
                            HappenedAfter.get(szActual).remove(szBefore); //Remove it from the "HappenedAfter", as it now has happened Before.
                            HappenedBefore.get(szBefore).remove(szActual); //also remove it the other way
                            Independents.get(szActual).add(szBefore); //It is now independent, according to the definition.
                            //ALSO, remove it from the After of the next ones.
                            HappenedAfter.get(szFollow).remove(szBefore); //Remove it from the "HappenedAfter", as it now has happened Before.
                            HappenedBefore.get(szBefore).remove(szFollow); //also remove it the other way
                            Independents.get(szFollow).add(szBefore); //It is now independent, according to the definition.
                            bFoundInconsistency = true;
                            continue;//Then, continue to next szBefore.
                        }
                    }
                    if(bFoundInconsistency == false)
                    {
                        //else, add "szBefore" to the ones that happened before szActual
                        HappenedBefore.get(szActual).add(szBefore);
                        HappenedAfter.get(szBefore).add(szActual);
                    }
                }
                //Now, erase the one that didn't appear, from the previous.
                HashSet<String> hsToRemove = new HashSet<>();
                for( String szPrevious : HappenedBefore.get(szActual) )
                {
                    if(sq.sequence.contains(szPrevious) == false)
                    {
                        //Then, szActual cannot be dependent of that activity
                        hsToRemove.add(szPrevious); //We store it to remove it after this cycle.
                        System.out.println("The Activity: " + szActual + " was seen without: " + szPrevious + " so they are now independent.");
                    }
                }
                //Now, just remove them. This is separate because it would otherwise modify the HappenedBefore collection while iterating it.
                for( String szPrevious :  hsToRemove)
                {
                    HappenedBefore.get(szActual).remove(szPrevious); //Remove it from the "HappenedBefore", as it now has NOT happened at all.
                    HappenedAfter.get(szPrevious).remove(szActual); //also remove it the other way
                    Independents.get(szActual).add(szPrevious); //It is now independent, according to the definition.
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
            //HashSet<String> hsTemp = new HashSet<>();
            //hsTemp.addAll( HappenedAfter.get(ActualLabel) );
            //HappenedAfter.get(ActualLabel).removeAll( HappenedBefore.get(ActualLabel) );
            //HappenedBefore.get(ActualLabel).removeAll( hsTemp);
            
            
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
