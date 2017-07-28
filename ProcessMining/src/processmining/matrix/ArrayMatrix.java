/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processmining.matrix;

import java.util.HashMap;
import processmining.Config;
import processmining.Graph.Graph;

/**
 * the matrix is in form of array 2D
 *
 * @author Humanoide
 */
public class ArrayMatrix {

    /*int matrix*/
    public int[][] intMatrix;
    /*binary matrix*/
    public int[][] binaryMatrix;
    
    int size=0;
    
    /**
     * create a new matrix
     */
    public ArrayMatrix() {
        /*
        initialize the matrix putting 0 in all cells
         */
        size=LabelNames.labelNamesList().size(); //PARRA! YOU HAD A HEAVY MISTAKE HERE, TOOK ME A WHILE TO FIND OUT.
        //you had "int size", but the size should be the one of the class.
        intMatrix=new int[size][size];
        binaryMatrix=new int[size][size];

    }
    /**
     * create matrix by the hash map generated in the mapMatrix
     * @param rows 
     */
    public ArrayMatrix(HashMap<String, TokenNames> rows){
        /*call the empty constructor*/
          this();
          size=LabelNames.labelNamesList().size();
          for (int i = 0; i < LabelNames.labelNamesList().size(); i++) {
            TokenNames tn = rows.get(LabelNames.labelNamesList().get(i));
            for (int j = 0; j < LabelNames.labelNamesList().size(); j++) {
                intMatrix[i][j]=tn.TokenMap.get(LabelNames.labelNamesList().get(j));
                if(intMatrix[i][j]>0){
                    binaryMatrix[i][j]=1;
                }
            }
        }
    }
    
    public ArrayMatrix Transpose()
    {
        ArrayMatrix pTransposed = new ArrayMatrix();
        for (int i = 0; i < LabelNames.labelNamesList().size(); i++) 
        {
            for (int j = 0; j < LabelNames.labelNamesList().size(); j++) 
            {
                pTransposed.binaryMatrix[i][j] = binaryMatrix[j][i]; 
            }
        }
        return pTransposed;
    }
    
    
    /**
     * create a new matrix by the graph
     * @param graph 
     */
    public ArrayMatrix(Graph graph){
        this(new MapMatrix().makeMatrix(graph));      
    }
    
    public int size(){
        return size;
    }

}
