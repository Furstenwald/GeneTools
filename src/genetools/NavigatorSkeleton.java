/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package genetools;

import genetools.COG.COG;
import genetools.GC.ATskew;
import genetools.GC.BDI;
import genetools.GC.GCcontent;
import genetools.GC.GCskew;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author btbase
 */
public class NavigatorSkeleton implements Serializable{
    private static final long serialVersionUID = -5456469641520525454L;
    

    int bplength = 0;

    Contig contig = null;
    double[] BDIvalues = null;
    byte[] GCvalues = null;
    byte[] GCskew = null;
    byte[] ATskew = null;

    Map<String, COG> cogMap = null;
    Map<String, Double> caiMap = null;

    public COG getCog(String shortcut)
    {
        return cogMap.get(shortcut);
    }

    public double getCai(String shortcut)
    {
        if(caiMap.get(shortcut)==null)
            return 0;
        else
            return caiMap.get(shortcut);
    }

    public double[] getBDI()
    {
        return BDIvalues;
    }

    public int getLength()
    {
        return bplength;
    }

    public Contig getContig()
    {
        return contig;
    }

    public byte[] getGCvalues()
    {
        return GCvalues;
    }

    public byte[] getGCskew()
    {
        return GCskew;
    }

    public byte[] getATskew()
    {
        return ATskew;
    }

    public NavigatorSkeleton(Contig EntireContig, HashMap<String, COG> cogmap, HashMap<String, Double> caimap)
    {
        bplength = EntireContig.getLength();
        contig = (Contig)EntireContig.clone();
        cogMap = cogmap;
        caiMap = caimap;

        contig.setSequence(null);

        for (Feature f:contig.featureSet)
        {
            if (f.isProteinProduct())
                f.getProteinProduct().setSequence(null);
        }


        BDI bdi = new BDI(((Species)(EntireContig.getParent())).contigs, EntireContig.getSequence(), 10000, 100);
        BDIvalues = bdi.getData();

        GCcontent gc = new GCcontent(EntireContig.getSequence(), 1000, 100);
        GCvalues = gc.getData();

        GCskew gcs = new GCskew(EntireContig.getSequence(), 1000, 100);
        GCskew = gcs.getData();

        ATskew ats = new ATskew(EntireContig.getSequence(), 1000, 100);
        ATskew = ats.getData();
    }

}
