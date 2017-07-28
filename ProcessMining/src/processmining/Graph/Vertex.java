/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processmining.Graph;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class represent a vertex in the graph
 * @author Humanoide
 */
public class Vertex {
    /*the name of the vertex*/
    private String label;
    /*childs*/
    //private ArrayList<Vertex> childs;
    
    private Vertex m_Parent ; //This one is used in the DFS Search. It points to the vertex which first visited this one during DFS.
    private boolean m_bVisited; //used to speed up the DFS process.
    
    private HashMap<String, Vertex> m_hmDescendants;
    private HashMap<String, Vertex> m_hmSuccessors;
    
    /*
    ==================================================================
    getters and setters
    */
    public boolean isVisited()
    {
        return m_bVisited;
    }
    
    public void setVisited(boolean in_bVisited)
    {
        m_bVisited = in_bVisited;
    }
    
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
    
    /*public ArrayList<Vertex> getChilds() {
        return childs;
    }*/
    
    public HashMap<String, Vertex> getSuccessors()
    {
        return m_hmSuccessors;
    }

    public HashMap<String, Vertex> getDescendants()
    {
        return m_hmDescendants;
    }
    
    /*public void setChilds(ArrayList<Vertex> childs) {
        this.childs = childs;
    }*/
    
    public void setSuccessors( HashMap<String, Vertex> in_hmSuccessors )
    {
        m_hmSuccessors = in_hmSuccessors;
    }
    
    public void setDescendants( HashMap<String, Vertex> in_hmDescendants )
    {
        m_hmDescendants = in_hmDescendants;
    }
    
    public void setParent ( Vertex in_Parent )
    {
        m_Parent = in_Parent;
    }
    
    public Vertex getParent (  )
    {
        return m_Parent;
    }
    /*===============================================================*/

    /**
     * initialize the vertex only with the name
     * @param label name of the vertex
     */
    public Vertex(String label) {
        this.label = label;
        /*initialize the array list*/
        //childs=new ArrayList<>();
        m_hmSuccessors = new HashMap<String, Vertex>();
        m_hmDescendants = new HashMap<String, Vertex>();
        m_Parent = null;
        m_bVisited = false;
    }
    /**
     * initialize the vertex with the name and the list of childs
     * @param label is the name of the vertex
     * @param childs is an array list
     */
    public Vertex(String label, ArrayList<Vertex> childs) {
        this.label = label;
        //this.childs = childs;
        m_hmSuccessors = new HashMap<String, Vertex>();
        m_hmDescendants = new HashMap<String, Vertex>();
        m_Parent = null;
        m_bVisited = false;
    }
    /**
     * add a child with the vertex
     * @param v 
     */
    /*public void addChild(Vertex v){
        childs.add(v);
    }*/
    
    /**
     * add a child by giving the label name of the vertex
     * @param graph is the graph where the vertex is
     * @param label the name of the vertex
     */
    /*public void addChild(Graph graph, String label){
        childs.add(graph.findByLabel(label));
    }*/
    
    public void AddSuccessor(Vertex in_vSucc)
    {
        m_hmSuccessors.put(in_vSucc.getLabel(), in_vSucc);
    }
    
    public void AddSuccessors( HashMap<String, Vertex> in_hmSuccs )
    {
        m_hmSuccessors.putAll(in_hmSuccs);
    }
    
    public void AddDescendant(Vertex in_vDes)
    {
        m_hmDescendants.put(in_vDes.getLabel(), in_vDes);
    }
    
     public void AddDescendants( HashMap<String, Vertex> in_hmDescendants )
    {
        m_hmDescendants.putAll(in_hmDescendants);
    }
}
