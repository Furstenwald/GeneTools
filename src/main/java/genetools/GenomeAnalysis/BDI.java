/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package genetools.GenomeAnalysis;

import genetools.Contig;
import genetools.sequenceHandling.Nucleotide;
import genetools.sequenceHandling.SequenceDNA;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
//import org.apache.commons.math.MathException;
import org.apache.commons.math3.stat.inference.*;

/**
 * Base deviation index
 * @author btbase
 */
public class BDI {
    double[] content;
    int length;
    int windowSize;
    int stepSize;
    int bplength;
    SequenceDNA seq;

    long[] baseFreq = new long[4];
    
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
    public double[] getData()
    {
        content = new double[length];
        for (int i=0; i<length; i++)
        {
            int start = i*stepSize-(windowSize/2);
            int stop = start+windowSize;
            int A = 0;
            int C = 0;
            int G = 0;
            int T = 0;
            //int total = 0;
            for (int k=start; k<stop; k++)
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
                if (seq.getByteArray()[actualK]==Nucleotide._C)
                    C++;
                if (seq.getByteArray()[actualK]==Nucleotide._G)
                    G++;
                if (seq.getByteArray()[actualK]==Nucleotide._T)
                    T++;
            }
            //total = A+C+G+T;
            long[] observed = new long[4];

            int actualnormalizedWindowSize = (stop-start)/1000;

            if (actualnormalizedWindowSize==0) //no division by 0
            {
                content[i]=0;
                continue;
            }

            observed[0]=A/actualnormalizedWindowSize;
            observed[1]=C/actualnormalizedWindowSize;
            observed[2]=G/actualnormalizedWindowSize;
            observed[3]=T/actualnormalizedWindowSize;

            long[][] chitest = {{observed[0], observed[1], observed[2], observed[3]}, {baseFreq[0], baseFreq[1], baseFreq[2], baseFreq[3]}};

            ChiSquareTest test = new ChiSquareTest();
            try {
               content[i] = test.chiSquare(chitest);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(BDI.class.getName()).log(Level.SEVERE, null, ex);
            } /*catch (MathException ex) {
                Logger.getLogger(BDI.class.getName()).log(Level.SEVERE, null, ex);
            }*/
        }
        return content;
    }

    /**
     *
     * @return
     */
    public long[] getBaseFrequencies()
    {
        return baseFreq;
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

    

    public BDI(ArrayList<Contig> cont, SequenceDNA sequenceOfInterest, int windowSize, int stepSize)
    {
        int A = 0;
        int C = 0;
        int G = 0;
        int T = 0;
        int total = 0;
        //Calculate overall distribution
        for (Contig c : cont)
        {
            c.getSequence().updateNucleotideCount();
            A += c.getSequence().getNucleotideCount()[Nucleotide._A];
            C += c.getSequence().getNucleotideCount()[Nucleotide._C];
            G += c.getSequence().getNucleotideCount()[Nucleotide._G];
            T += c.getSequence().getNucleotideCount()[Nucleotide._T];
            total += c.getLength();
        }

        //int total = A+C+G+T;

        long[] frequencies = new long[4];
        frequencies[0]=A/(total/1000); //normalize for 1000bp
        frequencies[1]=C/(total/1000);
        frequencies[2]=G/(total/1000);
        frequencies[3]=T/(total/1000);

        this.baseFreq = frequencies;
        this.seq = sequenceOfInterest;
        this.windowSize = windowSize;
        this.stepSize = stepSize;
        bplength = seq.getLength();
        length = bplength/stepSize;
    }
}
