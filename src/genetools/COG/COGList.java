/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package genetools.COG;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author btserver
 */
public class COGList implements Serializable{
    Map<Integer, COG> map = new HashMap<Integer, COG>();

    public void addCOG(COG newCOG)
    {
        map.put(newCOG.CogNumber, newCOG);
    }

    public COG get(int number)
    {
        return map.get(number);
    }
}
