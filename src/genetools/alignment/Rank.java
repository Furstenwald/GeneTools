/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package genetools.alignment;

import genetools.Feature;
import genetools.sequenceHandling.NamedSequenceAA;
import genetools.sequenceHandling.NamedSequenceDNA;


/**
 *
 * @author btserver
 */
public class Rank implements Comparable<Rank>{
    public int rank;
    public Feature feature;
    public NamedSequenceDNA seqdna;
    public NamedSequenceAA seqaa;

    public Rank(int rank, Feature seq)
    {
        this.rank = rank;
        this.feature = seq;
    }
    
    public Rank(int rank, NamedSequenceDNA seq)
    {
        this.rank = rank;
        this.seqdna = seq;
    }
    
    public Rank(int rank, NamedSequenceAA seq)
    {
        this.rank = rank;
        this.seqaa = seq;
    }

    @Override
    public int compareTo(Rank r)
    {
        return rank-r.rank;
    }
}
