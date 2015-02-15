/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package genetools.COG;

import java.io.Serializable;

/**
 *
 * @author btserver
 */
public class COGfunction implements Serializable{
    private static final long serialVersionUID = -1251786452728630526L;
    public char abbreviation;
    public String category;
    public String function;

    public COGfunction(char abbreviation, String category, String function)
    {
        this.abbreviation = abbreviation;
        this.category = category;
        this.function = function;
    }
}
