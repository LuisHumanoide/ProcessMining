
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
public class toolsRelations{
   public static HashMap<String,ArrayList<Character>> getListas(String contenido) {
        HashMap<Character, Character> tasks = new HashMap<>();
        char data[]=contenido.toCharArray();
        data=contenido.toCharArray();
        
        /*for(int i=0;i<data.length-1;i++)
            System.out.println("data:     "+data[i]);
        System.out.println("contenido:     "+contenido);*/
        
        for (int i = 0; i<=data.length-1; i++) 
            tasks.put(data[i], data[i]);
        //System.out.println("TASKS");
        
        boolean flag = false;
        int index = 0;
        HashMap<String, ArrayList<Character>> listas = new HashMap<>();
        ArrayList<Character> currentList = null;
 
        for (Character t : tasks.keySet()) {
            flag = false;
            index = 0;
            //System.out.println("CAMBIO DE T");
            for (int i = 0; i < data.length-1; i++) {
                if (data[i] == t && !flag) {
                    //System.out.println(" inicia lista de ["+t+""+index+"]");
                    flag = true;
                    currentList = new ArrayList();
                    currentList.add(data[i]);
                    listas.put(t + "" + index, currentList);

                } else if (data[i] == t && flag) {
                    flag = false;
                    index++;
                    i--;
                    //System.out.println(" fin lista ");
                } else if (flag) {
                    currentList.add(data[i]);
                   // System.out.println(t + " --> " + data[i]);
                }
            }
        }
        return listas;
    }
   
  public boolean comparar(char a, char b, ArrayList<char[]> pares){ // funciona al 100%
    boolean flat=false;
    char temp1, temp2; 
   for(int i=0; i<pares.size();i++)
    {
        for (char[] as : pares)
        {
            temp1=as[0];
            temp2=as[1];
            if(temp1==a && temp2==b)
                return flat; //elemento contenido
        }
    }   
   return flat=true;
  }
    
}

