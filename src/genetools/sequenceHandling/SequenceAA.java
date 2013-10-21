/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package genetools.sequenceHandling;


/**
 *
 * @author kingcarlxx
 */
public class SequenceAA extends Sequence {
    /**
     *
     */
    private static final long serialVersionUID = -917788507021051520L;
    private static final Aminoacids aminos = new Aminoacids();
    private transient int[] AminoAcidCount = new int[Aminoacids.alphabetSize];

    /**
     * Create <code>SequenceDNA</code> using a string, illegal aminoacids are converted to Gaps (-).
     * @param seq
     */
    public SequenceAA(String seq)
    {
        setSequence(seq);
    }

    /**
     * Updates AminoacidCount Array
     */
    public void updateAminoacidCount()
    {
        AminoAcidCount = aminos.countAminos(sequence);
    }

    /**
     * Get AminoacidCount Array
     * @return Int-Array: Index of Array: Aminoacid-Value in <code>Aminoacids</code> class; Value of Array: counted occurrences of this aminoacid
     */
    public int[] getAminoacidCount()
    {
        return AminoAcidCount;
    }

    /**
     * Create <code>SequenceAA</code> using a byte array.
     * @param seq
     */
    public SequenceAA(byte[] seq)
    {
        sequence = seq;
    }
    
    /**
     * Returns this sequence as string
     * @return
     */
    public String toString()
    {
        return aminos.bytearray2string1Letter(sequence);
    }

    /**
    * Sets sequence of this object using a string
    * @param seq
    */
    public void setSequence(String seq)
    {
        sequence = aminos.string2bytearray(seq);
    }

    /**
    * Deletes all Gaps (-)
    */
    public SequenceAA killGaps()
    {
        return new SequenceAA(aminos.killGaps(sequence));
    }

}
