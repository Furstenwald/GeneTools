/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genetools.NGS;

import genetools.sequenceHandling.Position;
import genetools.sequenceHandling.SequenceTools;
import java.util.Arrays;
import java.util.Collections;

/**
 *
 * @author Rainer
 */
public class NGSTools {
    
    public static QualitySequenceDNA getSubsequence(QualitySequenceDNA qs, int start, int stop)
    {
        byte[] qual = new byte[stop-start+1];
        if (qual.length<1)
            return null;
        System.arraycopy(qs.getQuality(), start, qual, 0, stop-start+1);
        return new QualitySequenceDNA(qs.getName(),qs.getSequence().getLinearSubSequence(new Position(start, stop, false)),qual);
    }
    
    public static QualitySequenceDNA getRevComp(QualitySequenceDNA qs)
    {
        byte[] qual = new byte[qs.getQuality().length];
        
        for (int i=0;i<qs.getQuality().length;i++)
            qual[i]=qs.getQuality()[qs.getQuality().length-1-i];
        return new QualitySequenceDNA(qs.getName(),SequenceTools.reverseComplement(qs.getSequence(), true, true),qual);
    }
    
    
    
}
