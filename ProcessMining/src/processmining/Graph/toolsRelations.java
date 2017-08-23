package processmining.Graph;

import com.google.common.collect.BiMap;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import processmining.gui.FileUtils;
import processmining.actions.Process;
import processmining.Graph.Sequence;
import com.google.common.collect.Sets;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import processmining.matrix.LabelNames;

/**
 *
 * @author xmora
 */
public class toolsRelations {

    public static HashMap<String, ArrayList<Character>> coca(char data[]) {

        /**
         * CREA EL ARREGLO DE LISTAS POR LETRITA
         */
        HashMap<Character, ArrayList<ArrayList<Character>>> taskList = new HashMap<>();

        HashMap<Character, Character> tasks = new HashMap<>();

        for (int i = 0; i < data.length; i++) {
            tasks.put(data[i], data[i]);
        }

        //System.out.println("TASKS");
        boolean flag = false;
        int index = 0;

        HashMap<String, ArrayList<Character>> listas = new HashMap<>();
        ArrayList<ArrayList<Character>> listasPorLetrita = null;
        ArrayList<Character> currentList = null;

        for (Character t : tasks.keySet()) {

            listasPorLetrita = new ArrayList<ArrayList<Character>>();
            flag = false;
            index = 0;

            taskList.put(t, listasPorLetrita);

           // System.out.println("CAMBIO DE T");
            for (int i = 0; i < data.length; i++) {
                if (data[i] == t && !flag) {

                    if(i == data.length - 1)continue;
                    
                    //System.out.println(" inicia lista de ["+t+""+index+"]");
                    flag = true;
                    currentList = new ArrayList();
                    currentList.add(data[i]);

                    listasPorLetrita.add(currentList);

                    //listas.put(t + "" + index, currentList);
                } else if (data[i] == t && flag) {
                    flag = false;

                    index++;
                    i--;

                     //System.out.println(" fin lista ");
                } else if (flag) {
                    currentList.add(data[i]);
                    //System.out.println(t + " --> " + data[i]);
                }
            }

        }
        
        HashMap<Character,ArrayList<Character>> letritaYsusamigos = new HashMap<>(); //<--- ESTE ES EL CHIDO
        ArrayList<Character> repetidas = null;

        for (Character tareaAnalizar : taskList.keySet()) {
            
            repetidas = new ArrayList<>();
            letritaYsusamigos.put(tareaAnalizar, repetidas);
            
            for (Character letritaAVer : taskList.keySet()) {
                if (letritaAVer.charValue() == tareaAnalizar.charValue()) {
                    repetidas.add(tareaAnalizar);
                    continue;
                }
                int total = 0;
                //System.out.println("Verificando " + letritaAVer + " en listas de " + tareaAnalizar);
                
                ArrayList<ArrayList<Character>> todasLasListasDeLaLetrita = taskList.get(tareaAnalizar);
                for (ArrayList<Character> listaPorLetrita : todasLasListasDeLaLetrita) {
                    if (listaPorLetrita.contains(letritaAVer)) {
                        total++;
                        //System.out.print("Esta lista si contiene a " + letritaAVer + ": [");

                    } else {
                        //System.out.print("Esta lista no contiene a " + letritaAVer+ ": [");
                    }
                    for (Character character : listaPorLetrita) {
                        //System.out.print(character);
                    }
                    //System.out.print("]\n");
                }
                if(total == todasLasListasDeLaLetrita.size()){
                    repetidas.add(letritaAVer);
                }
                //System.out.println("La letrita aparecio en "+total+" de "+todasLasListasDeLaLetrita.size());

            }

        }
        
        //
        
        System.out.println(" Repetitively dependet ");

        for(Character key:letritaYsusamigos.keySet()){
            System.out.print("Rd("+key+")"+": {");
            for (Character character : letritaYsusamigos.get(key)) {
                System.out.print(character+" ");
            }
            System.out.println("}");
        }
        
        /*
        for (Character key : taskList.keySet()) {

            System.out.println("Letrita " + key);

            for (ArrayList<Character> listaPorLetrita : taskList.get(key)) {
                System.out.println("Lista ");

                for (Character letrita : listaPorLetrita) {
                    System.out.print(letrita + " ");
                }
                System.out.println("");

            }

        }*/

        return listas;
    }





    public boolean comparar(char a, char b, ArrayList<char[]> pares) { 
        boolean flat = false;
        char temp1, temp2;
        for (int i = 0; i < pares.size(); i++) {
            for (char[] as : pares) {
                temp1 = as[0];
                temp2 = as[1];
                if (temp1 == a && temp2 == b) {
                    return flat; //elemento contenido
                }
            }
        }
        return flat = true;
    }
    public boolean compararTc(char a, char b, char c, ArrayList<char[]> pares) { 
        boolean flat = false;
        char temp1, temp2, temp3;
        for (int i = 0; i < pares.size(); i++) {
            for (char[] as : pares) {
                temp1 = as[0];
                temp2 = as[1];
                temp3 = as[2]; 
                if (temp1 == a && temp2 == b && temp3 == c) {
                    return flat; //elemento contenido
                }
            }
        }
        return flat = true;
    }

}
