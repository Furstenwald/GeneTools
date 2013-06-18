/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package genewarrior.COG;

import genewarrior.sequenceHandling.SequenceAA;
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
