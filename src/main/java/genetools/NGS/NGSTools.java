/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genetools.NGS;

import genetools.sequenceHandling.Position;
import genetools.sequenceHandling.SequenceTools;

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
    
    public static byte[] convertQualities(String qualities) {
    	byte q[] = qualities.trim().getBytes();
        for (int i=0;i<q.length;i++)
        {
            q[i]=(byte)(q[i]-33);
        }
        return q;
    }
    
    public static String convertQualities(byte[] qualities) {
    	String q="";
    	for (int i=0;i<qualities.length;i++)
        {
            char c = (char)(qualities[i]+33);
            q+=c;
        }
    	return q;
    }
    
    
}
