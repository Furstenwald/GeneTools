/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package genetools.COG;

import genetools.sequenceHandling.SequenceAA;
import java.io.Serializable;

/**
 *
 * @author btserver
 */
public class COGsequence implements Serializable{
    public SequenceAA seq;
    public COG cog;

    public COGsequence(SequenceAA seq, COG cog)
    {
        this.seq = seq;
        this.cog = cog;
    }
}
