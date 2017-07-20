/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processmining.gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Humanoide
 */
public class FileUtils {

    /**
     * read the file and return a string
     *
     * @param path
     * @return the content of the file
     */
    public static String readFile(File file) {
        String sCurrentLine = null;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            while ((sCurrentLine = br.readLine()) != null) {
                System.out.println(sCurrentLine);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return sCurrentLine;
    }

}
