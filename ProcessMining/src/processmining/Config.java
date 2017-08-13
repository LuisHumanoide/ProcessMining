/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processmining;

import static processmining.Config.splitCharacter.SINGLE;



/**
 *
 * @author Humanoide
 */
public class Config {

    public enum splitCharacter {
        SINGLE, SPACE;
    }
    public static boolean canPrint = true;
    public static  splitCharacter splitCharacterSelected = SINGLE;
}
