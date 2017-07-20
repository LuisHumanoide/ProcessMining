/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processmining.Graph;

import processmining.matrix.Matrix;
import java.util.ArrayList;

/**
 *
 * @author Humanoide
 */
public interface Transformations {
    /**
     * convert the incidence matrix to a graph
     * @return 
     */
    public Vertex MatrixToGraph(Matrix m);
    /**
     * convert the actions to incidence matrix
     * @param actions 
     */
    public void ActionsToMatrix(ArrayList<Sequence> actions);
    
}
