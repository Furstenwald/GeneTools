/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genetools.NGS;

import java.io.*;

/**
 *
 * @author corona
 */
public class QualityFileWrite {

    BufferedWriter out;
    public QualityFileWrite(File FASTQ) throws FileNotFoundException, IOException
    {
        out = new BufferedWriter(new FileWriter(FASTQ));
    }
    

    public void write(QualitySequenceDNA qs) throws IOException
    {
        out.write("@"+qs.getName());out.newLine();
        
        out.write(qs.getSequence().toString());out.newLine();
        out.write("+");out.newLine();
        for (int i=0;i<qs.getQuality().length;i++)
        {
            char c = (char)(qs.getQuality()[i]+33);
            out.write(c);
        }
        out.newLine();
    }
  
    public void close() throws IOException {
        out.close();
    }

}
