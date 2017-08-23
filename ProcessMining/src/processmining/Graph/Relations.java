/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processmining.Graph;

import com.google.common.collect.BiMap;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import processmining.gui.FileUtils;
import processmining.actions.Process;
import processmining.Graph.Sequence;
import processmining.Graph.toolsRelations;
import com.google.common.collect.Sets;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import processmining.matrix.LabelNames;

/**
 *
 * @author xmora
 */
public class Relations {

    ArrayList<char[]> seqPar; //contiene pares ordenados
    HashMap<Character, String> task;
    TreeMap<String, ArrayList<String>> rd;
    toolsRelations obj = new toolsRelations();
    ArrayList<char[]> tc;

    public Relations() {
        seqPar = new ArrayList<>();
        task = new HashMap<>();
        rd = new TreeMap<>();
        tc = new ArrayList<>();
    }

    public void SequenceParOrdenado(File list) { // funciona al 100%
        String contenido;
        contenido = FileUtils.readFile(list);
        char uno;
        char dos;
        String t1 = "";
        String t2 = "";

        for (int i = 0; i < contenido.length() - 2; i++) {
            uno = contenido.charAt(i);
            dos = contenido.charAt(i + 1);
            task.put(uno, "");
            task.put(dos, "");
            if (seqPar.isEmpty()) {
                seqPar.add(new char[]{uno, dos});
            } else if (obj.comparar(uno, dos, seqPar) == true) {
                seqPar.add(new char[]{uno, dos});
            }
        }

        System.out.println("- - - - - - - - - - - - - -");
        System.out.println(" Secuencia de pares ");
        System.out.print(" Seq = {");
        Iterator i = seqPar.iterator();
        while (i.hasNext()) {
            char[] par = (char[]) i.next();
            System.out.print("(" + par[0] + "," + par[1] + ")");
        }
        System.out.println("}");
        System.out.println("- - - - - - - - - - - - - -");
        System.out.print("Lista de tareas: {");
        for (Character character : task.keySet()) {
            System.out.print(character + ", ");
        }
        System.out.println("}");
        concurrenteR();
        obj.coca(contenido.trim().toCharArray());
        twoCycle(contenido.trim());
    }

    public void concurrenteR() {
        ArrayList<char[]> conR = new ArrayList<>();
        char t1, t2, tn1, tn2;
        boolean flat = false;
        for (char[] par : seqPar) {
            t1 = par[0];
            t2 = par[1];
            for (char[] anidado : seqPar) {
                tn1 = anidado[0];
                tn2 = anidado[1];
                if (t1 == tn2 && t2 == tn1) {
                    //System.out.println("Es su inverso...."+ t1+" "+t2+" inverso "+tn1+" "+tn2);
                    conR.add(anidado);
                    flat = true;
                }
            }
        }
        System.out.println("- - - - - - - - - - - - - -");
        System.out.println(" Relaciones concurrentes ");
        Iterator it = conR.iterator();
        if (flat == true) {
            System.out.print("ConcR = { ");
            while (it.hasNext()) {
                char[] parCo = (char[]) it.next();
                System.out.print("(" + parCo[0] + "," + parCo[1] + ")");
            }
            System.out.println("}");
        } else {
            System.out.println("La secuencia no contiene relaciones concurrentes ");
        }
        System.out.println("- - - - - - - - - - - - - -");
    }

    public void twoCycle(String contenido) {
        char uno, dos, tres;
        toolsRelations obj = new toolsRelations();
        //System.out.println("Contenido:   "+contenido);
        for (int i = 0; i < contenido.length() - 2; i++) {
            uno = contenido.charAt(i);
            dos = contenido.charAt(i + 1);
            tres = contenido.charAt(i + 2);
            if (uno == tres) 
                tc.add(new char[]{uno, dos, tres}); 
        }
        Iterator i = tc.iterator();
        if(!tc.isEmpty()){
            System.out.print("TC={");
            while (i.hasNext()) {
                char[] tercia = (char[]) i.next();
                System.out.print("("+tercia[0] + "," + tercia[1] + "," + tercia[2]+") ");
            }
            System.out.println("}");
        
        }else
            System.out.println(" la secuencia no contiene TC ");
        
    }

}
