/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processmining.Graph;

/**
 *
 * @author Humanoide
 */
public class Label {
    String label;
    int counter=1;
    /*========================================================================
    GETTERS AND SETTERS
    */
    public String getLabel() {
        return label;
    }

    public int getCounter() {
        return counter;
    }

    public void setLabel(String label) {
        this.label = label;
    }
    //=========================================================================
    /**
     * constructor 
     * @param label 
     */
    
    public Label(String label) {
        this.label = label;
    }
    /**
     * increase the number of occurrences 
     */
    public void setCounter(int counter){
        this.counter=counter;
    }
    
    
}
