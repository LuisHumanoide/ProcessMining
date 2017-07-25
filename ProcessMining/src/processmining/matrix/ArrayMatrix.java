/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processmining.matrix;

import java.util.HashMap;
import processmining.Config;

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
        int size=LabelNames.labelNamesList().size();
        intMatrix=new int[size][size];
        binaryMatrix=new int[size][size];
        /*
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                intMatrix[i][j]=0;
                binaryMatrix[i][j]=0;
            }
        }*/
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
    
    public int size(){
        return size;
    }

}
