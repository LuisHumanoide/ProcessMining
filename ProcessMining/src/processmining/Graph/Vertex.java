/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processmining.Graph;

import java.util.ArrayList;

/**
 * This class represent a vertex in the graph
 * @author Humanoide
 */
public class Vertex {
    /*the name of the vertex*/
    private String label;
    /*childs*/
    private ArrayList<Vertex> childs;
    /*
    ==================================================================
    getters and setters
    */
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
    
    public ArrayList<Vertex> getChilds() {
        return childs;
    }

    public void setChilds(ArrayList<Vertex> childs) {
        this.childs = childs;
    }
    /*===============================================================*/

    /**
     * initialize the vertex only with the name
     * @param label name of the vertex
     */
    public Vertex(String label) {
        this.label = label;
        /*initialize the array list*/
        childs=new ArrayList<>();
    }
    /**
     * initialize the vertex with the name and the list of childs
     * @param label is the name of the vertex
     * @param childs is an array list
     */
    public Vertex(String label, ArrayList<Vertex> childs) {
        this.label = label;
        this.childs = childs;
    }
    /**
     * add a child with the vertex
     * @param v 
     */
    public void addChild(Vertex v){
        childs.add(v);
    }
    
    /**
     * add a child by giving the label name of the vertex
     * @param graph is the graph where the vertex is
     * @param label the name of the vertex
     */
    public void addChild(Graph graph, String label){
        childs.add(graph.findByLabel(label));
    }
    
    
    
}
