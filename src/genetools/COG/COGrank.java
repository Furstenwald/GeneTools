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
public class COGrank implements Comparable<COGrank>, Serializable{
    private static final long serialVersionUID = -3756760497856158146L;
    public int rank;
    public COG cog;

    public COGrank(int rank, COG cog)
    {
        this.rank = rank;
        this.cog = cog;
    }

    public int compareTo(COGrank r)
    {
        return r.rank-rank;
    }
}
