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
    //TreeMap<String, ArrayList<String>> rd;
    toolsRelations obj = new toolsRelations();
    ArrayList<char[]> tc;
    HashMap<Character, ArrayList<Character>> letritaYsusamigos;
    ArrayList<char[]> conR;
    ArrayList<char[]> cauR;

    public Relations() {
        seqPar = new ArrayList<>();
        task = new HashMap<>();
        //rd = new TreeMap<>();
        tc = new ArrayList<>();
        letritaYsusamigos = new HashMap<>();
        conR = new ArrayList<>();
        cauR = new ArrayList<>();
    }

    public void SequenceParOrdenado(File list) { // funciona al 100%
        String contenido;
        contenido = FileUtils.readFile(list);
        System.out.println("secuencia==============   " + contenido);
        char uno;
        char dos;
        String t1 = "";
        String t2 = "";

        for (int i = 0; i < contenido.length() - 2; i++) {
            uno = contenido.charAt(i);
            dos = contenido.charAt(i + 1);

            if (uno != '\n' && dos != '\n') {
                task.put(uno, "");
                task.put(dos, "");
                if (seqPar.isEmpty()) {
                    seqPar.add(new char[]{uno, dos});
                } else if (obj.comparar(uno, dos, seqPar) == true) {
                    seqPar.add(new char[]{uno, dos});
                }
            }
            if (uno == '\n' && dos != '\n') {
                uno = contenido.charAt(i + 1);
                dos = contenido.charAt(i + 2);
                if (obj.comparar(uno, dos, seqPar) == true) {
                    seqPar.add(new char[]{uno, dos});
                }
                i++;
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
        System.out.println("- - - - - - - - - - - - - -");

        repetitiveDependent(contenido.trim().toCharArray());
        twoCycle(contenido.trim());
        concurrenteR(contenido.trim());
        causarR();
    }

    public void concurrenteR(String contenido) {
        char t1, t2, tn1, tn2;
        boolean flat = false;
        for (char[] par : seqPar) {
            t1 = par[0];
            t2 = par[1];
            for (char[] anidado : seqPar) {
                tn1 = anidado[0];
                tn2 = anidado[1];
                if (t1 == tn2 && t2 == tn1) {
                    if (obj.compararTc(t1, t2, t1, tc) == true) {
                        conR.add(anidado);
                        flat = true;
                    }
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
        for (int i = 0; i < contenido.length() - 2; i++) {
            uno = contenido.charAt(i);
            dos = contenido.charAt(i + 1);
            tres = contenido.charAt(i + 2);
            if (tc.isEmpty() && uno == tres) {
                tc.add(new char[]{uno, dos, tres});
            } else if (uno == tres && obj.compararTc(uno, dos, tres, tc) == true) {
                tc.add(new char[]{uno, dos, tres});
            }
        }
        Iterator i = tc.iterator();
        if (!tc.isEmpty()) {
            System.out.print("TC={");
            while (i.hasNext()) {
                char[] tercia = (char[]) i.next();
                System.out.print("(" + tercia[0] + "," + tercia[1] + "," + tercia[2] + ") ");
            }
            System.out.println("}");

        } else {
            System.out.println(" la secuencia no contiene TC ");
        }

    }

    public void causarR() {
        ArrayList<char[]> temporal = new ArrayList(seqPar);
        char par1, par2;
        boolean flagConcurrente = false;

        Iterator i = temporal.iterator();
        while (i.hasNext()) {
            flagConcurrente = false;
            char[] par = (char[]) i.next();
            par1 = par[0];
            par2 = par[1];
            char tn1, tn2;
            for (char[] anidado : conR) {
                tn1 = anidado[0];
                tn2 = anidado[1];
                if (tn1 == par1 && tn2 == par2) {
                    flagConcurrente = true;
                }
            }

            if (flagConcurrente == false) {
                for (Character key : letritaYsusamigos.keySet()) {
                    if (key.equals(par1)) {
                        for (Character character : letritaYsusamigos.get(key)) {
                            if (character.equals(par2)) {
                                if (cauR.isEmpty()) {
                                    cauR.add(par);
                                } else if (obj.comparar(par1, par2, cauR) == true) {
                                    cauR.add(par);
                                }
                            }
                        }
                    } else if (key.equals(par2)) {
                        for (Character character : letritaYsusamigos.get(key)) {
                            if (character.equals(par1)) {
                                if (cauR.isEmpty()) {
                                    cauR.add(par);
                                } else if (obj.comparar(par1, par2, cauR) == true) {
                                    cauR.add(par);
                                }
                            }
                        }
                    }
                }

                if (obj.compararTc(par1, par2, par1, tc) == false) {
                    if (obj.comparar(par1, par2, cauR) == true) {
                        cauR.add(par);
                    }
                }
            }
        }

        if (!cauR.isEmpty()) {
            System.out.println("Relacion causal");
            Iterator it = cauR.iterator();
            System.out.print("CausalR = { ");
            while (it.hasNext()) {
                char[] parCo = (char[]) it.next();
                System.out.print("(" + parCo[0] + "," + parCo[1] + ")");
            }
            System.out.println("}");
        } else {
            System.out.println("La secuencia no contiene relaciones causales ");
        }

        System.out.println(
                "- - - - - - - - - - - - - -");

    }

    public HashMap<String, ArrayList<Character>> repetitiveDependent(char data[]) {

        /**
         * CREA EL ARREGLO DE LISTAS POR LETRITA
         */
        HashMap<Character, ArrayList<ArrayList<Character>>> taskList = new HashMap<>();

        HashMap<Character, Character> tasks = new HashMap<>();

        for (int i = 0; i < data.length; i++) {
            if(data[i]!='\n')
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

            System.out.println("CAMBIO DE T");
            for (int i = 0; i < data.length; i++) {
              if(data[i]!='\n'){
                if (data[i] == t && !flag) {

                    if (i == data.length - 1) {
                        continue;
                    }

                    System.out.println(" inicia lista de ["+t+""+index+"]");
                    flag = true;
                    currentList = new ArrayList();
                    currentList.add(data[i]);
                    
                    

                    listas.put(t + "" + index, currentList);
                } else if (data[i] == t && flag) {
                    flag = false;
                    listasPorLetrita.add(currentList);
                    index++;
                    i--;

                    System.out.println(" fin lista ");
                } else if (flag) {
                    currentList.add(data[i]);
                    System.out.println(t + " --> " + data[i]);
                }
            }
            }
        }

        //HashMap<Character,ArrayList<Character>> letritaYsusamigos = new HashMap<>(); //<--- ESTE ES EL CHIDO
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
                if (total == todasLasListasDeLaLetrita.size()) {
                    repetidas.add(letritaAVer);
                }
                //System.out.println("La letrita aparecio en "+total+" de "+todasLasListasDeLaLetrita.size());

            }

        }

        //
        System.out.println(" Repetitively dependet ");

        for (Character key : letritaYsusamigos.keySet()) {
            System.out.print("Rd(" + key + ")" + ": {");
            for (Character character : letritaYsusamigos.get(key)) {
                System.out.print(character + " ");
            }
            System.out.println("}");
        }
        System.out.println("- - - - - - - - - - - - - -");

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

}
