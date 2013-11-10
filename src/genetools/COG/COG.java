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
public class COG implements Serializable{
    private static final long serialVersionUID = -1340406985966590149L;

    public int CogNumber;
    public String CogName;
    public COGfunction cogfunction;

    public COG(int CogNumber, String CogName, COGfunction cogfunction)
    {
        this.CogNumber = CogNumber;
        this.CogName = CogName;
        this.cogfunction = cogfunction;
    }

}
