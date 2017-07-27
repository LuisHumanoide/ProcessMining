/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processmining.actions;

import java.util.ArrayList;
import java.util.LinkedList;
import javafx.collections.transformation.SortedList;
import processmining.Config;
import processmining.Graph.Graph;
import processmining.Graph.Vertex;
import processmining.Graph.Sequence;
import processmining.matrix.ArrayMatrix;
import processmining.matrix.LabelNames;
import processmining.matrix.MapMatrix;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

/**
 *
 * @author Humanoide
 */
public class Process {
    /**
     * extract the sequences by the file
     * @param logFile
     * @return 
     */
    public static LinkedList<Sequence> getSequences(String logFile) {
        LabelNames.clearList();
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
        Graph graph1=new Graph(am);
        graph1.makeGraphviz("grafo1");
    }

    /**
     * Function for TESTING ONLY, it reproduces the example given in slides 21 to 23 in the Power point presentation-
     * it is correctly right now 27 July 2017
     * @param in_Graph the graph to reduce.
     */
    public static void TransitiveReduction(Graph in_Graph)
    {
        //1. Find a topological ordering of G
        //The selected topological order will be alphabetically. this
        
        
        //2. for each v, let the succesors of v be succ(v) = {u|(v, u) belong to E}.
        System.out.println("Checking that the Successors are correctly initialized");
        
        /* 3.
            for each vertex v, in reverse topological order:
                a) set the descendants of v equal to the union of the descendants of its successors.
                b) If a successor of v is also a descendant of v, remove it from the successors of v.
                c) Add the remanining successors of v to its descendants.
        */
        ArrayList<Vertex> lsInverseTopologicalOrder;
        lsInverseTopologicalOrder = new ArrayList<Vertex>( in_Graph.getNodes() );
        lsInverseTopologicalOrder.sort(Comparator.comparing(Vertex::getLabel).reversed()); //This gives us the array of vertices in the inverted order.
        
        //This has to be done in a separate cycle, otherwise, the successors weren't set correctly.
        for( Vertex V : lsInverseTopologicalOrder )
        {
            //this is the point "2", : for each v, let the succesors of v be succ(v) = {u|(v, u) belong to E}.
            //for( Vertex Child : V.getChilds())
            //    V.AddSuccessor(Child);
            V.setSuccessors(V.getDescendants()); // this is weird, is a notation issue only.
            
            V.setDescendants(new HashMap<>()); // so it is now empty, according to the algorithm.
        }
        
        HashSet<Vertex> hsGraphVertices = new HashSet<Vertex>();
        for( Vertex V : lsInverseTopologicalOrder )
        {
            
            //a)
            for( Vertex SuccV : V.getSuccessors().values() )
            {
                V.AddDescendants(SuccV.getDescendants());
            }
            //b)
            //This is an Asymmetric difference of sets. Therefore, it removes all of the successors that also are descendants
            V.getSuccessors().keySet().removeAll( V.getDescendants().keySet() ); 
            
            //c)
            V.AddDescendants(V.getSuccessors());
            
            hsGraphVertices.add(V); // this is only used to set the nodes to the final graph.
        }
       
        //Now, just print it in the console to see the state of the operation
        System.out.println("The graph structure, after the transitive reduction, is now: ");
        for(Vertex V : hsGraphVertices)
        {
            System.out.println("Node " + V.getLabel() + " descendants are: ");
            for(String DesVLabel : V.getDescendants().keySet())
            {
                System.out.print(DesVLabel + ", ");
            }
            //The same, but with the successors:
            System.out.println("Node " + V.getLabel() + " Successors are: ");
            for(String SuccVLabel : V.getSuccessors().keySet())
            {
                System.out.print(SuccVLabel + ", ");
            }
            System.out.println("END.");
            
            //NOTE: IMPORTANT PLEASE READ:
            //The ones noted as "Successors" in the algorithm, are the ones
            //that are the Children, so here I stablish them as the Descendants.
            V.setDescendants(V.getSuccessors());
        } 
        
        //4. Return the graph (V, {(v, u)| u belongs to succ(v)}).
        in_Graph.setNodes(hsGraphVertices); //the result remains stored at "in_Graph".
    }
    
      /**
     * Function for TESTING ONLY, it seems to work properly with example stated there. (27 July 2017)
     */
    public static void TestTransitiveReduction ()
    {
        //This is based on the example of "http://www.geeksforgeeks.org/strongly-connected-components/".
        System.out.println("Enter of TestTransitiveReduction function.");
        
        HashSet<Vertex> hsTestNodes = new HashSet<>();
        
        Vertex vA = new Vertex("A" );
        Vertex vB = new Vertex("B" );
        Vertex vC = new Vertex("C" );
        Vertex vD = new Vertex("D" );
        Vertex vE = new Vertex("E" );
        
        
        
        vA.AddDescendant(vB);
        vA.AddDescendant(vC);
        vA.AddDescendant(vD);
        vA.AddDescendant(vE);
        
        vB.AddDescendant(vE);
        
        vC.AddDescendant(vD);
        vC.AddDescendant(vE);
        
        vD.AddDescendant(vE);
        
        //E has nothing
        
        hsTestNodes.add(vA);
        hsTestNodes.add(vB);
        hsTestNodes.add(vC);
        hsTestNodes.add(vD);
        hsTestNodes.add(vE);
        /**
         * for test purpuses, clean the labelnames and add the names again
         */
        /*
        LabelNames.clearList();
        for(Vertex v:hsTestNodes){
            LabelNames.addLabelNames(v.getLabel());
        }
        */
        Graph pGraph = new Graph(hsTestNodes);
        System.out.println("Testing transitive reduction.");
        TransitiveReduction(pGraph);
        //this method works with descendants
        //pGraph.makeGraphviz2("asdf");
        System.out.println("Exit of TestTransitiveReduction function.");
    }
    
    
    
    
    /**
     * Function for TESTING ONLY, it seems to work properly with example stated there. (27 July 2017)
     */
    public static void TestStronglyConnected ()
    {
        //This is based on the example of "http://www.geeksforgeeks.org/strongly-connected-components/".
        System.out.println("Enter of TestStronglyConnected function.");
        
        HashSet<Vertex> hsTestNodes = new HashSet<>();
        
        Vertex v0 = new Vertex("0" );
        Vertex v1 = new Vertex("1" );
        Vertex v2 = new Vertex("2" );
        Vertex v3 = new Vertex("3" );
        Vertex v4 = new Vertex("4" );
        
        v0.AddDescendant(v2);
        v0.AddDescendant(v3);
        v1.AddDescendant(v0);
        v2.AddDescendant(v1);
        v3.AddDescendant(v4);
        //v4 has nothing.
        
        hsTestNodes.add(v0);
        hsTestNodes.add(v1);
        hsTestNodes.add(v2);
        hsTestNodes.add(v3);
        hsTestNodes.add(v4);
        
        
        Graph pGraph = new Graph(hsTestNodes);
        System.out.println("Testing Get SCCs.");
        GetStronglyConnectedComponents(pGraph);
        
        System.out.println("Exit of TestStronglyConnected function.");
    }
    
    
    /**
     * Calculates the Strongly connected components of a graph. It is based on the KOSARAJU's algorithm. 
     * but has been adapted to be Iterative, not recursive (so it has no problem with larger graphs).
     * @param in_pGraph the graph in which you want to find SCCs.
     * @return a container with all the SCCs in the form of
     */
    public static ArrayList<ArrayList<Vertex>> GetStronglyConnectedComponents( Graph in_pGraph )
    {
        //Each arraylist of vertices represents a SCC, so the bigger arraylist has ALL the SCCs of the graph
        ArrayList<ArrayList<Vertex>> pComponents = new ArrayList<>(  );
        Stack<Vertex> sDFSStack = new Stack<>();
        
        //This for is to ensure that all nodes get analyzed, even if the graph is disconnected.
        for( Vertex V : in_pGraph.getNodes() )
        {
            if(V.isVisited())
            {
                //Then, it is not necessary
                continue;
            }
            //else, it means this one has not been pushed onto the stack, so we start a DFS recursive from it.
            V.setVisited(true);//set it visited so it doesn't cycle.
            Vertex VActual = V;
            while(true)
            {
                //First, check all its adjacent vertices, and apply DFS recursively on them.
                int iCounter = 0;
                for(Vertex AdjV : VActual.getDescendants().values())
                {
                    if(AdjV.isVisited() == false)
                    {
                        //Then, call DFS from that node.
                        AdjV.setVisited(true); //now it counts as being visited, so it is not processed again.
                        AdjV.setParent( VActual);
                        VActual = AdjV; //Now the actual is equal to that adjacent vertex. the DFS will now continue from it.
                        iCounter = -1; //set it to an invalid value, so it doesn't cause trouble.
                        break;
                    }
                    iCounter++;
                }
                //if the number of 
                if(iCounter == VActual.getDescendants().size())
                {
                    //Then this has 
                    //Then, once there are no more adjacent Unvisited vertices to this one, push it onto the stack. 
                    sDFSStack.push(VActual);
                    //Then, restablish the VActual as the parent of the one who has been just pushed onto the stack.
                    if( VActual.getParent() != null ) //if it has a Parent, means that the DFS has not yet finished.
                    {
                        VActual = VActual.getParent();
                    }
                    else // if it enters here, it means that this vertex is the Root of one of the DFSs (Strongly connected components).
                    {
                        break; //Break the while (true). So we can continue with any other node missing ( in case of disconnected graphs.) 
                    }
                    
                }
            }
        }
        
        System.out.println("The DFS process for fill ordering has finished, the obtained order is: ");
        //Now, print to check the order in which elements are in the stack.
        for( Vertex V : sDFSStack )
        {
            System.out.print( V.getLabel() + ", " );
        }
        
        //To this point, the process seems to be ok.
        //Now: 2) Reverse directions of all arcs to obtain the transpose graph.
        
        
        
        return pComponents;
    }
    
    /**
     * It does equivalent computations to the Algorithm 3 (Cyclic graphs), in the presentation.
     * Given a log L of executions of a process, find the dependency graph G.
     * @param in_DependencyGraph is the dependency graph obtained from the sequences given as input.
     * @param in_lsInputSequences is a list containing all the sequences given the input. It is used mark which edges belong to sequences and which ones don't.
     */
    public static void GraphAnalysis (Graph in_DependencyGraph, LinkedList<Sequence> in_lsInputSequences )
    {
        /*1. Start with the graph G = (V, E), with V being the set of activities of the process and E = 0.*/
   
        /*2. Go through each execution in the log and uniquely identify each activity recorded in the log, 
        thus create a new set of vertices V' and graph G = (V' , E). */
    
        /*3. For each process execution in L, and for each pair of activities u, v such that u terminates
        before v starts, add the edge (u, v) to E'. (In practice, steps 1-3 are executed
        together in one pass over the log.)*/
       
        //SEGÚN YO, HASTA AQUÍ ES DONDE LO DEJÓ PARRA.
    
        /*4. Remove from E' the edges that appear in both directions. */
        //This has to be tested.
        
        /*5. For each strongly connected component of G', remove from E' all edges between
        vertices in the same strongly connected component.*/
        //Make a function to find strongly connected components.
        
        /*6. For each process execution present in the log: 
        
            A) find the induced subgraph of G' (it is vertices present in that execution and all the vertices connecting them).
            B) Compute the transitive reduction of the subgraph.
            C) Mark those edges in E' that are present in the transitive reduction.
        */
        
        /*7. Remove the unmarked edges in E' */
        
       
        
        /*8. In the graph so obtained, merge the vertices that correspond to different instances of
        the same activity in the graph, thus reverting to the original set of vertices*/
        
        /*9. Return the resulting graph.*/
    }
    
    
    
    
    
    
}
