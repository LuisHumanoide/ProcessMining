/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processmining.actions;

import java.util.LinkedList;
import processmining.Config;
import processmining.Graph.Graph;
import processmining.Graph.Sequence;
import processmining.matrix.ArrayMatrix;
import processmining.matrix.LabelNames;
import processmining.matrix.MapMatrix;

/**
 *
 * @author Humanoide
 */
public class Process {

    public static LinkedList<Sequence> getSequences(String logFile) {
        /*the log file is splitted, and each division represent a sequence*/
        String[] arraySequence = logFile.split("\n");        
        /*the list of sequences is initialized*/
        LinkedList<Sequence> sequenceList = new LinkedList<Sequence>();

        /*for each line in the log, the sequence is proceced*/
        for (String sequence : arraySequence) {
            Sequence sq=new Sequence();
            /*the line is procecced char by char*/
            for (int i = 0; i < sequence.length(); i++) {
                sq.addNode(sequence.charAt(i) + "");
            }
            /*the sequence is added to the list*/
            sequenceList.add(sq);
            sq.printSequence();
            System.out.println();
        }
        return sequenceList;
    }
    /**
     * do the process receiving the string of the sequences
     * @param content 
     */
    public static void doProcess(String content){
        //create the sequences
        LinkedList<Sequence> sq=Process.getSequences(content);
        //print the sequences
        if (Config.canPrint) System.out.println(LabelNames.labelNamesList());
        MapMatrix m=new MapMatrix();
        m.makeMatrix(sq);
        m.printMatrix();
        ArrayMatrix am=new ArrayMatrix(m.Rows);
        Graph graph=new Graph(am);
    }

}
