/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package genewarrior.alignment;

import genewarrior.Feature;


/**
 *
 * @author btserver
 */
public class Rank implements Comparable<Rank>{
    public int rank;
    public Feature seq;

    public Rank(int rank, Feature seq)
    {
        this.rank = rank;
        this.seq = seq;
    }

    @Override
    public int compareTo(Rank r)
    {
        return rank-r.rank;
    }
}
