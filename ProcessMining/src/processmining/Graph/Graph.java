/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processmining.Graph;

import java.util.HashSet;

/**
 * class that represent the graph
 *
 * @author Humanoide
 */
public class Graph {

    HashSet<Vertex> nodes;
    HashSet<Vertex> startNodes;

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
    public Vertex findByLabel(String label){
        for(Vertex v:nodes){
            if(v.equals(label)){
                return v;
            }
        }
        return null;
    }
    
    
    
    

}
