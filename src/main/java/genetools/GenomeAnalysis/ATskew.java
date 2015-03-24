/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package genetools.GenomeAnalysis;

import genetools.sequenceHandling.Nucleotide;
import genetools.sequenceHandling.SequenceDNA;

/**
 * Holds the GCcontent of sliding Window
 * @author btbase
 */
public class ATskew {
    byte[] content;
    int length;
    int windowSize;
    int stepSize;
    int bplength;
    
    /**
     * The length of the sequence
     * @return
     */
    public int getSequenceLength()
    {
        return bplength;
    }
    
    /**
     * Get the length of the array holding the GCcontent
     * @return
     */
    public int getDataLength()
    {
        return length;
    }
    
    /**
     * Returns the content of the sliding window as the percentage of GC
     * @return
     */
    public byte[] getData()
    {
        return content;
    }
    
    /**
     * Returns the Size of the Window
     * @return
     */
    public int getWindowSize()
    {
        return windowSize;
    }

    /**
     * Returns the Size of the steps
     * @return
     */
    public int getStepSize()
    {
        return stepSize;
    }

    

    public ATskew(SequenceDNA seq, int windowSize, int stepSize)
    {
        bplength = seq.getLength();
        length = seq.getLength()/stepSize;
        this.windowSize = windowSize;
        this.stepSize = stepSize;

        content = new byte[length];
        
        for (int i=0;i<length;i++)
        {
            int A = 0;
            int T = 0;
            
            int start = (i*stepSize)-(windowSize/2);
            int end = (start+windowSize)-1;

            for (int k=start;k<end+1;k++)
            {
                int actualK = k;
                if (k<0)
                    actualK=seq.getLength()+k;
                else if(k > seq.getLength() - 1)
                    actualK=k-seq.getLength();

                if (actualK<0||actualK>seq.getLength()-1) //happens only if windowSize is bigger than seq.getLength
                    continue;

                if (seq.getByteArray()[actualK]==Nucleotide._A)
                    A++;
                else if(seq.getByteArray()[actualK] == Nucleotide._T)
                    T++;
            }

            content[i]=(byte)(((double)(A-T))/((double)(A+T))*100);
        }

    }

}
