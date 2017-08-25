/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processmining.Graph;

import java.util.HashMap;
import java.util.HashSet;
import processmining.gui.FileUtils;
import processmining.gui.GraphFrame;
import processmining.matrix.ArrayMatrix;
import processmining.matrix.LabelNames;
import processmining.matrix.MapMatrix;

/**
 * class that represent the graph
 *
 * @author Humanoide
 */
public class Graph {

    HashSet<Vertex> nodes;
    HashSet<Vertex> startNodes;
    //No estoy usando edges

    /*==========================================================
    GETTERS AND SETTERS
     */
    public HashSet<Vertex> getNodes() {
        return nodes;
    }

    public void setNodes(HashSet<Vertex> nodes) {
        this.nodes = nodes;
    }

    public HashSet<Vertex> getStartNodes() {
        return startNodes;
    }

    public void setStartNodes(HashSet<Vertex> startNodes) {
        this.startNodes = startNodes;
    }
    //=========================================================

    /**
     * that constructor need the set of nodes and initial nodes
     * @param nodes
     * @param startNodes 
     */
    public Graph(HashSet<Vertex> nodes, HashSet<Vertex> startNodes) {
        this.nodes = nodes;
        this.startNodes = startNodes;
    }
    
    /**
     * this constructor need the set of nodes
     * @param nodes 
     */
    public Graph(HashSet<Vertex> nodes) {
        this.nodes = nodes;
        startNodes=new HashSet<>();
    }

    /**
     * empty constructor
     */
    public Graph() {
        nodes=new HashSet<>();
        startNodes=new HashSet<>();
        
    }
    /**
     * search the vertex by the giving label in the graph
     * @param label
     * @return 
     */
    public Graph(ArrayMatrix am){
        this();
        for(String name:LabelNames.labelNamesList()){
            nodes.add(new Vertex(name));
        }
        for(int i=0;i<am.size();i++){
            Vertex vtx=this.findByLabel(LabelNames.get(i));
            
            for(int j=0;j<am.size();j++){
                if(am.binaryMatrix[i][j]==1){
                    Vertex vtx2=this.findByLabel(LabelNames.get(j));
                    vtx.AddDescendant(vtx2);
                }
            }
        }
        /*
        for(int i=0;i<am.size();i++){
            Vertex vtx=this.findByLabel(LabelNames.get(i));
            
            for(int j=0;j<am.size();j++){
                if(am.binaryMatrix[i][j]==1){
                    Vertex vtx2=this.findByLabel(LabelNames.get(j));
                    vtx.AddDescendant(vtx2);
                }
            }
        }
        */
                
    }
    /**
     * generate the graph in graphviz software
     */
    public void makeGraphviz(String name){
        String content="digraph G{\n";
        for(Vertex v:nodes){
            for(Vertex v2:v.getDescendants().values()){
                content=content+"\""+v.getLabel()+"\" -> "+"\""+v2.getLabel()+"\"\n";
            }
        }
        content=content+"}";
        FileUtils.write(name, content,"txt");
        FileUtils.generateImg(name, "png");
        new GraphFrame(name);   
    }
    
       public void makeGraphviz2(String name){
        String content="digraph G{\n";
        for(Vertex v:nodes){
            for(Vertex v2:v.getDescendants().values()){
                content=content+"\""+v.getLabel()+"\" -> "+"\""+v2.getLabel()+"\"\n";
            }
        }
        content=content+"}";
        FileUtils.write(name, content,"txt");
        FileUtils.generateImg(name, "png");
        new GraphFrame(name);   
    }
    
    public Graph(MapMatrix mm){
        //code here
    }
    /**
     * find a vertex by the giving label
     * @param label
     * @return 
     */
    public Vertex findByLabel(String label){
        for(Vertex v:nodes){
            if(v.getLabel().equals(label)){
                return v;
            }
        }
        return null;
    }
    
    
    /**
     * 
     * @param in_Sequence
     * @return
     */
    public Graph getInducedSubgraph (Sequence in_Sequence)
    {
        //WARNING: THE NODES OF THE SUBGRAPH MUST BE DIFFERENT INSTANCES THAN THE ONES IN THE ORIGINAL GRAPH, OTHERWISE, WEIRD THINGS MAY HAPPEN.
        HashMap<String, Vertex> pNodes = new HashMap<>(); //HashSet to store the nodes which DO belong to this sequence.
        for( String Names : in_Sequence.sequence)
        {
            pNodes.put(Names, new Vertex(Names));
        }
        for (Vertex V :  pNodes.values())
        {
            Vertex V2 = findByLabel(V.getLabel()); //This is the node in the ORIGINAL GRAPH, not the induced one.
            for( String V_Des : V2.getDescendants().keySet() ) //Iterate through its descendants, to make the connections in the Induced one.
            {
                if(pNodes.containsKey(V_Des)) //If this Node name is in the Sequence
                {
                    V.AddDescendant(pNodes.get(V_Des)); //Then, we DO add this edge to the induced subgraph
                }
            }
        }    
        //Finally, assign the nodes to the Graph instance to be return.
        HashSet<Vertex> pFinalVertices = new HashSet<>();
        pFinalVertices.addAll(pNodes.values());
        //create a graph with vertices named after the Nodes in the sequences.
        Graph pInduced = new Graph(  pFinalVertices);
        
        return pInduced;
        
    }
    

}
