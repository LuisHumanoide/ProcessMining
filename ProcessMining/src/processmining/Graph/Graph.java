/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processmining.Graph;

import java.util.HashSet;
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
                    vtx.addChild(vtx2);
                }
            }
        }
        
    }
    
    public Graph(MapMatrix mm){
        
    }
    
    public Vertex findByLabel(String label){
        for(Vertex v:nodes){
            if(v.getLabel().equals(label)){
                return v;
            }
        }
        return null;
    }
    
    
    
    

}
